package by.epam.taskXML.entity;

public enum PlantOrigin {
    RUSSIAN,
    NETHERLANDS,
    GERMANY,
    SPAIN,
    UKRAINE;

    @Override
    public String toString() {
        String result = this.name();
        return result.substring(0,1).toUpperCase() + result.substring(1).toLowerCase();
    }
}
