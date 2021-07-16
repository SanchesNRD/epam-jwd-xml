package by.epam.taskXML.entity;

import java.time.YearMonth;

public abstract class Plant {
    public static final String DEFAULT_MANUFACTURE = "greenhouse";

    private String id;
    private String manufactureName;
    private String name;
    private YearMonth plantTime;
    private PlantOrigin origin;
    private PlantSoil soil;
    private GrowingTips tips;

    public Plant(){
        this.manufactureName = DEFAULT_MANUFACTURE;
        this.plantTime = YearMonth.now();
    }

    public Plant(String id, String manufactureName, String name, YearMonth plantTime, PlantOrigin origin, PlantSoil soil, GrowingTips tips) {
        this.id = id;
        this.manufactureName = manufactureName;
        this.name = name;
        this.plantTime = plantTime;
        this.origin = origin;
        this.soil = soil;
        this.tips = tips;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YearMonth getPlantTime() {
        return plantTime;
    }

    public void setPlantTime(YearMonth plantTime) {
        this.plantTime = plantTime;
    }

    public PlantOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(PlantOrigin origin) {
        this.origin = origin;
    }

    public PlantSoil getSoil() {
        return soil;
    }

    public void setSoil(PlantSoil soil) {
        this.soil = soil;
    }

    public GrowingTips getTips() {
        return tips;
    }

    public void setTips(GrowingTips tips) {
        this.tips = tips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Plant plant = (Plant) o;
        return id.equals(plant.id) && manufactureName.equals(plant.manufactureName) &&
                name.equals(plant.name) && plantTime.equals(plant.plantTime) &&
                origin == plant.origin && soil == plant.soil && tips.equals(plant.tips);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + id.hashCode();
        result = result * 31 + manufactureName.hashCode();
        result = result * 31 + name.hashCode();
        result = result * 31 + plantTime.hashCode();
        result = result * 31 + origin.hashCode();
        result = result * 31 + soil.hashCode();
        result = result * 31 + tips.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n")
                .append(getClass().getSimpleName()).append(":\n")
                .append("id = ").append(id).append("\n")
                .append("manufactureName = ").append(manufactureName).append("\n")
                .append("name = ").append(name).append("\n")
                .append("plantTime = ").append(plantTime).append("\n")
                .append("origin = ").append(origin).append("\n")
                .append("soil = ").append(soil).append("\n")
                .append(tips);

        return stringBuilder.toString();
    }
}
