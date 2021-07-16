package by.epam.taskXML.builder;

import java.util.Locale;

public enum PlantXmlTag {
    GREENHOUSE,
    TREE,
    FLOWER,
    NAME,
    PLANT_TIME,
    ORIGIN,
    SOIL,
    GROWING_TIPS,
    LIGHT,
    TEMPERATURE,
    SORT,
    HEIGHT,
    COLOR;

    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT)
                .replaceAll(UNDERSCORE, HYPHEN);
    }
}
