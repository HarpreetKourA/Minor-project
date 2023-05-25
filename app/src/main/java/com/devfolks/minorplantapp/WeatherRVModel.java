package com.devfolks.minorplantapp;

public class WeatherRVModel {

    private String date, temperature, icon, windSpeed;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }


    public WeatherRVModel(String date, String temperature, String icon, String windSpeed) {
        this.date = date;
        this.temperature = temperature;
        this.icon = icon;
        this.windSpeed = windSpeed;
    }

}
