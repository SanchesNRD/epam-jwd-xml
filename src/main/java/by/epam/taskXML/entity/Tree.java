package by.epam.taskXML.entity;

import java.time.YearMonth;

public class Tree extends Plant{
    private double height;
    private TreeSort sort;

    public Tree() {
    }

    public Tree(String id, String manufactureName, String name, YearMonth plantTime, PlantOrigin origin, PlantSoil soil, GrowingTips tips, int height, TreeSort sort) {
        super(id, manufactureName, name, plantTime, origin, soil, tips);
        this.height = height;
        this.sort = sort;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public TreeSort getSort() {
        return sort;
    }

    public void setSort(TreeSort sort) {
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

        Tree tree = (Tree) o;
        return height == tree.height && sort == tree.sort;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + Double.hashCode(height);
        result = result * 31 + sort.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(super.toString())
                .append("height = ").append(height).append("\n")
                .append("sort = ").append(sort).append("\n");

        return stringBuilder.toString();
    }
}
