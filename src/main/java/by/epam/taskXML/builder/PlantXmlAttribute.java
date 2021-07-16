package by.epam.taskXML.builder;

import java.util.Locale;

public enum PlantXmlAttribute {
    ID,
    MANUFACTURER_NAME;

    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT)
                .replaceAll(UNDERSCORE, HYPHEN);
    }
}
