package by.epam.taskXML.builder;

import by.epam.taskXML.exception.PlantEntityException;


public class PlantBuilderFactory {
    private enum ParserType{
        DOM,SAX,STAX
    }

    private PlantBuilderFactory(){
    }

    public static PlantBuilder createPlantBuilder(String parserName) throws PlantEntityException {

        ParserType parserType = ParserType.valueOf(parserName.toUpperCase());
        return switch (parserType) {
            case DOM -> new DomPlantBuilder();
            case SAX -> new SaxPlantBuilder();
            case STAX -> new StaxPlantBuilder();
            default -> throw new PlantEntityException("parser was not found. Name = " + parserName);
        };
    }
}
