package by.epam.taskXML.builder;

import by.epam.taskXML.entity.*;
import by.epam.taskXML.exception.PlantEntityException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.YearMonth;

public class DomPlantBuilder extends PlantBuilder{
    private final DocumentBuilder documentBuilder;

    public DomPlantBuilder() throws PlantEntityException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new PlantEntityException("incorrect parser configurations", e);
        }
    }

    @Override
    public void buildPlants(String xmlPath) throws PlantEntityException {
        Document document;

        try{
            document = documentBuilder.parse(xmlPath);
            Element root = document.getDocumentElement();

            String flowersTag = PlantXmlTag.FLOWER.toString();
            String treesTag = PlantXmlTag.TREE.toString();
            NodeList flowers = root.getElementsByTagName(flowersTag);
            NodeList trees = root.getElementsByTagName(treesTag);

            for(int i = 0; i < flowers.getLength(); i++){
                Element element = (Element) flowers.item(i);
                Plant plant = new Flower();
                buildEntity(element, plant);
                plants.add(plant);
            }

            for(int i = 0; i < trees.getLength(); i++){
                Element element = (Element) trees.item(i);
                Plant plant = new Tree();
                buildEntity(element, plant);
                plants.add(plant);
            }

        } catch (SAXException e) {
           throw new PlantEntityException("xml file was not parse. xmlPath = " + xmlPath);
        } catch (IOException e) {
            throw new PlantEntityException("xml file was not open. xmlPath = " + xmlPath);
        }
    }

    private void buildEntity(Element element, Plant plant){
        String idAttribute = PlantXmlAttribute.ID.toString();
        String manufactureNameAttribute = PlantXmlAttribute.MANUFACTURER_NAME.toString();
        String nameTag = PlantXmlTag.NAME.toString();
        String plantTimeTag = PlantXmlTag.PLANT_TIME.toString();
        String originTag = PlantXmlTag.ORIGIN.toString();
        String soilTag = PlantXmlTag.SOIL.toString();
        String lightTag = PlantXmlTag.LIGHT.toString();
        String temperatureTag = PlantXmlTag.TEMPERATURE.toString();
        String sortTag = PlantXmlTag.SORT.toString();
        String colorTag = PlantXmlTag.COLOR.toString();
        String heightTag = PlantXmlTag.HEIGHT.toString();

        String id = element.getAttribute(idAttribute);
        String manufactureName = element.getAttribute(manufactureNameAttribute);
        String name = getTagValue(nameTag, element);
        YearMonth plantTime = YearMonth.parse(getTagValue(plantTimeTag, element));
        PlantOrigin origin = PlantOrigin.valueOf(getTagValue(originTag, element).toUpperCase());
        PlantSoil soil = PlantSoil.valueOf(getTagValue(soilTag, element).toUpperCase());
        boolean light = Boolean.parseBoolean(getTagValue(lightTag, element));
        int temperature = Integer.parseInt(getTagValue(temperatureTag, element));

        if(!manufactureName.isEmpty()){
            plant.setManufactureName(manufactureName);
        }

        plant.setId(id);
        plant.setName(name);
        plant.setPlantTime(plantTime);
        plant.setOrigin(origin);
        plant.setSoil(soil);
        plant.setTips(new GrowingTips(light, temperature));

        if (plant.getClass() == Flower.class){
            Flower flower = (Flower) plant;
            FlowerSort flowerSort = FlowerSort.valueOf(getTagValue(sortTag, element).toUpperCase());
            String color = getTagValue(colorTag, element);
            flower.setSort(flowerSort);
            flower.setColor(color);
        }else{
            Tree tree = (Tree) plant;
            TreeSort treeSort = TreeSort.valueOf(getTagValue(sortTag, element).toUpperCase());
            double height = Double.parseDouble(getTagValue(heightTag, element));
            tree.setHeight(height);
            tree.setSort(treeSort);
        }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nlList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }
}
