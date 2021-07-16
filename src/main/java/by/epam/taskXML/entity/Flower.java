package by.epam.taskXML.entity;

import java.time.YearMonth;

public class Flower extends Plant{
    private String color;
    private FlowerSort sort;

    public Flower() {
    }

    public Flower(String id, String manufactureName, String name, YearMonth plantTime, PlantOrigin origin, PlantSoil soil, GrowingTips tips, String color, FlowerSort sort) {
        super(id, manufactureName, name, plantTime, origin, soil, tips);
        this.color = color;
        this.sort = sort;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public FlowerSort getSort() {
        return sort;
    }

    public void setSort(FlowerSort sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        Flower flower = (Flower) o;
        return color.equals(flower.color) && sort == flower.sort;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + color.hashCode();
        result = result * 31 + sort.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString())
                .append("color = ").append(color).append("\n")
                .append("sort = ").append(sort).append("\n");

        return stringBuilder.toString();
    }
}
