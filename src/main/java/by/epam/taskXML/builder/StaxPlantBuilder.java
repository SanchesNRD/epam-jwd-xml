package by.epam.taskXML.builder;

import by.epam.taskXML.entity.*;
import by.epam.taskXML.exception.PlantEntityException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.time.YearMonth;
import java.util.Optional;

public class StaxPlantBuilder extends PlantBuilder{
    private final XMLInputFactory inputFactory;

    private boolean currentLight;
    private int currentTemperature;

    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";

    private final String flower = PlantXmlTag.FLOWER.toString();
    private final String tree = PlantXmlTag.TREE.toString();
    private final String tips = PlantXmlTag.GROWING_TIPS.toString();

    public StaxPlantBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildPlants(String xmlPath) throws PlantEntityException {
        XMLStreamReader streamReader;
        String name;

        try {
            Source source = new StreamSource(xmlPath);
            streamReader = inputFactory.createXMLStreamReader(source);

            while(streamReader.hasNext()){
                int type = streamReader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = streamReader.getLocalName();

                    if (name.equals(flower)) {
                        Plant plant = new Flower();
                        buildPlantEntity(plant, streamReader);
                        plants.add(plant);
                    }

                    if(name.equals(tree)){
                        Plant plant = new Tree();
                        buildPlantEntity(plant, streamReader);
                        plants.add(plant);
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new PlantEntityException("", e);
        }
    }

    private void buildPlantEntity(Plant plant, XMLStreamReader streamReader) throws XMLStreamException, PlantEntityException {
        String idAttribute = PlantXmlAttribute.ID.toString();
        String manufactureNameAttribute = PlantXmlAttribute.MANUFACTURER_NAME.toString();
        String id = streamReader.getAttributeValue(null, idAttribute);
        String manufactureName = streamReader.getAttributeValue(null, manufactureNameAttribute);

        plant.setId(id);
        if(manufactureName != null){
            plant.setManufactureName(manufactureName);
        }

        String name;
        while (streamReader.hasNext()){
            int type = streamReader.next();

            switch (type){
                case XMLStreamConstants.START_ELEMENT -> {
                    name = streamReader.getLocalName();
                    PlantXmlTag tag = PlantXmlTag.valueOf(toXmlTag(name));
                    initializationEntity(tag, plant, streamReader);
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = streamReader.getLocalName();
                    if(name.equals(tips)){
                        plant.setTips(new GrowingTips(currentLight, currentTemperature));
                    }

                    if(name.equals(flower) || name.equals(tree)){
                        return;
                    }
                }
            }
        }
        throw new PlantEntityException("Unable to build Plant object");
    }

    private void initializationEntity(PlantXmlTag tag, Plant plant, XMLStreamReader streamReader) throws XMLStreamException, PlantEntityException {
        String data = getTextContent(streamReader)
                .orElseThrow(() -> new PlantEntityException("Unable to get text content"));

        if(tag == PlantXmlTag.GROWING_TIPS){
            return;
        }

        switch (tag){
            case NAME -> plant.setName(data);
            case PLANT_TIME -> plant.setPlantTime(YearMonth.parse(data));
            case ORIGIN -> plant.setOrigin(PlantOrigin.valueOf(toXmlTag(data)));
            case SOIL -> plant.setSoil(PlantSoil.valueOf(toXmlTag(data)));
            case LIGHT -> currentLight = Boolean.parseBoolean(data);
            case TEMPERATURE -> currentTemperature = Integer.parseInt(data);
            case SORT -> {
                if(plant.getClass() == Flower.class){
                    Flower flower = (Flower) plant;
                    flower.setSort(FlowerSort.valueOf(toXmlTag(data)));
                }
                else{
                    Tree tree = (Tree) plant;
                    tree.setSort(TreeSort.valueOf(toXmlTag(data)));
                }
            }
            case HEIGHT -> {
                Tree tree = (Tree) plant;
                tree.setHeight(Double.parseDouble(data));
            }
            case COLOR -> {
                Flower flower = (Flower) plant;
                flower.setColor(data);
            }
            default -> throw new EnumConstantNotPresentException(
                    tag.getDeclaringClass(),tag.name());
        }
    }

    private String toXmlTag(String name){
        return name.trim()
                .toUpperCase()
                .replaceAll(HYPHEN, UNDERSCORE);
    }

    private Optional<String> getTextContent(XMLStreamReader reader) throws XMLStreamException {
        Optional<String> result = Optional.empty();

        if(reader.hasNext()){
            reader.next();
            String text = reader.getText();
            result = Optional.of(text);
        }

        return result;
    }
}
