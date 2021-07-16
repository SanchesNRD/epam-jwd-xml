package by.epam.taskXML;

import by.epam.taskXML.builder.DomPlantBuilder;
import by.epam.taskXML.builder.PlantBuilder;
import by.epam.taskXML.builder.PlantBuilderFactory;
import by.epam.taskXML.exception.PlantEntityException;

public class Main {
    public static void main(String[] args) {
        try {
            PlantBuilder PlantBuilder = PlantBuilderFactory.createPlantBuilder("SAX");
            PlantBuilder.buildPlants("src\\main\\resources\\greenhouse.xml");
            System.out.println(PlantBuilder.getPlants().toString());
        } catch (PlantEntityException e) {
            System.out.println(e);
        }
    }
}
