package by.epam.taskXML.entity;


public class GrowingTips {
    private boolean light;
    private int temperature;

    public GrowingTips(){
    }

    public GrowingTips(boolean light, int temperature) {
        this.light = light;
        this.temperature = temperature;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrowingTips that = (GrowingTips) o;
        return light == that.light && temperature == that.temperature;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + Integer.hashCode(temperature);
        result = result * 31 + Boolean.hashCode(light);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("light = ").append(light).append("\n")
                .append("temperature = ").append(temperature).append("\n");
        return stringBuilder.toString();
    }
}
