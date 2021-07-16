package by.epam.taskXML.exception;

public class PlantEntityException extends Exception{
    public PlantEntityException() {
    }

    public PlantEntityException(String message) {
        super(message);
    }

    public PlantEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlantEntityException(Throwable cause) {
        super(cause);
    }
}
