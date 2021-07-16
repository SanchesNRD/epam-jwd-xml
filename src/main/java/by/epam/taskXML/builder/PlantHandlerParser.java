package by.epam.taskXML.builder;


import by.epam.taskXML.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.YearMonth;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class PlantHandlerParser extends DefaultHandler {
    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";

    private final Set<Plant> plants;
    private final EnumSet<PlantXmlTag> xmlTags;
    private Plant currentPlant;
    private PlantXmlTag currentTag;

    private boolean currentLight;
    private int currentTemperature;

    public PlantHandlerParser() {
        plants = new HashSet<>();
        xmlTags = EnumSet.range(PlantXmlTag.NAME, PlantXmlTag.COLOR);
    }

    public Set<Plant> getPlants(){
        return plants;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String flower = PlantXmlTag.FLOWER.toString();
        String tree = PlantXmlTag.TREE.toString();

        if(tree.equals(qName) || flower.equals(qName)){
            currentPlant = tree.equals(qName) ? new Tree() : new Flower();

            String idAttribute = PlantXmlAttribute.ID.toString();
            String manufactureNameAttribute = PlantXmlAttribute.MANUFACTURER_NAME.toString();

            for(int i = 0; i < attributes.getLength(); i++){
                String attributeName = attributes.getQName(i);

                if(attributeName.equals(idAttribute)){
                    String id = attributes.getValue(i);
                    currentPlant.setId(id);
                }else if(attributeName.equals(manufactureNameAttribute)){
                    String manufactureName = attributes.getValue(i);
                    if(!manufactureName.isEmpty()){
                        currentPlant.setManufactureName(manufactureName);
                    }
                }else{
                    System.out.println("unknown attribute " + attributeName);
                }
            }
        }
        else{
            PlantXmlTag tag = PlantXmlTag.valueOf(toXmlTag(qName));

            if(xmlTags.contains(tag)){
                currentTag = tag;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String flower = PlantXmlTag.FLOWER.toString();
        String tree = PlantXmlTag.TREE.toString();
        String tips = PlantXmlTag.GROWING_TIPS.toString();

        if(tips.equals(qName)){
            currentPlant.setTips(new GrowingTips(currentLight, currentTemperature));
        }

        if(tree.equals(qName) || flower.equals(qName)){
            plants.add(currentPlant);
            currentPlant = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length);

        if(currentTag == null || currentTag == PlantXmlTag.GROWING_TIPS){
            return;
        }

        switch (currentTag){
            case NAME -> currentPlant.setName(data);
            case PLANT_TIME -> currentPlant.setPlantTime(YearMonth.parse(data));
            case ORIGIN -> currentPlant.setOrigin(PlantOrigin.valueOf(toXmlTag(data)));
            case SOIL -> currentPlant.setSoil(PlantSoil.valueOf(toXmlTag(data)));
            case LIGHT -> currentLight = Boolean.parseBoolean(data);
            case TEMPERATURE -> currentTemperature = Integer.parseInt(data);
            case SORT -> {
                if(currentPlant.getClass() == Flower.class){
                    Flower plant = (Flower) currentPlant;
                    plant.setSort(FlowerSort.valueOf(toXmlTag(data)));
                }
                else{
                    Tree plant = (Tree) currentPlant;
                    plant.setSort(TreeSort.valueOf(toXmlTag(data)));
                }
            }
            case HEIGHT -> {
                Tree plant = (Tree) currentPlant;
                plant.setHeight(Double.parseDouble(data));
            }
            case COLOR -> {
                Flower plant = (Flower) currentPlant;
                plant.setColor(data);
            }
            default -> throw new EnumConstantNotPresentException(
                    currentTag.getDeclaringClass(),currentTag.name());
        }

        currentTag = null;
    }

    private String toXmlTag(String name){
        return name.trim()
                .toUpperCase()
                .replaceAll(HYPHEN, UNDERSCORE);
    }
}
