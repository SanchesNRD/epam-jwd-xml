package by.epam.taskXML.builder;

import by.epam.taskXML.entity.Plant;
import by.epam.taskXML.exception.PlantEntityException;

import java.util.HashSet;
import java.util.Set;

public abstract class PlantBuilder {
    protected Set<Plant> plants;

    public PlantBuilder(){
        plants = new HashSet<>();
    }

    public Set<Plant> getPlants(){
        return plants;
    }

    public abstract void buildPlants(String xmlPath) throws PlantEntityException;
}
