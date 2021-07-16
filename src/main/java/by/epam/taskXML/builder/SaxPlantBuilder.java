package by.epam.taskXML.builder;

import by.epam.taskXML.exception.PlantEntityException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxPlantBuilder extends PlantBuilder{
    private final XMLReader reader;
    private final PlantHandlerParser handlerParser;

    public SaxPlantBuilder() throws PlantEntityException{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        handlerParser = new PlantHandlerParser();
        try {
            SAXParser parser = factory.newSAXParser();
            reader = parser.getXMLReader();
            reader.setContentHandler(handlerParser);
        } catch (ParserConfigurationException | SAXException e) {
            throw new PlantEntityException("incorrect parser configurations", e);
        }
    }

    @Override
    public void buildPlants(String xmlPath) throws PlantEntityException {
        try {
            reader.parse(xmlPath);
        } catch (SAXException e) {
            throw new PlantEntityException("xml file was not parse. xmlPath = " + xmlPath);
        } catch (IOException e) {
            throw new PlantEntityException("xml file was not open. xmlPath = " + xmlPath);
        }

        plants = handlerParser.getPlants();
    }
}
