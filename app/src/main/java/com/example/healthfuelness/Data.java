package com.example.healthfuelness;

public class Data {
    private String temperature;
    private String humidity;
    private String uvLevel;
    private String airQuality;
    private String toluene;
    private String acetone;
    private String ammonia;
    private String alcohol;
    private String dioxideCarbon;
    private String hydrogen;

    public Data(String temperature, String humidity, String uvLevel, String airQuality, String toluene,
                String acetone, String ammonia, String alcohol, String hydrogen, String dioxideCarbon) {

        this.temperature = temperature;
        this.humidity = humidity;
        this.uvLevel = uvLevel;
        this.airQuality = airQuality;
        this.toluene = toluene;
        this.acetone = acetone;
        this.ammonia = ammonia;
        this.alcohol = alcohol;
        this.dioxideCarbon = dioxideCarbon;
        this.hydrogen = hydrogen;
    }

    public String getTemperature() { return this.temperature;}
    public String getAcetone() { return this.acetone;}
    public String getAirQuality() { return this.airQuality;}
    public String getAlcohol() { return this.alcohol;}
    public String getAmmonia() { return this.ammonia;}
    public String getHumidity() { return this.humidity;}
    public String getDioxideCarbon() { return this.dioxideCarbon;}
    public String getToluene() { return this.toluene;}
    public String getHydrogen() { return this.hydrogen;}
    public String getUvLevel() { return this.uvLevel;}
    public void setTemperature(String temperature) {this.temperature = temperature;}
    public void setAcetone(String acetone) { this.acetone = acetone;}
    public void setAirQuality(String airQuality) { this.airQuality = airQuality;}
    public void setAlcohol(String alcohol) { this.alcohol = alcohol;}
    public void setAmmonia(String ammonia) { this.ammonia = ammonia;}
    public void setDioxideCarbon(String dioxideCarbon) { this.dioxideCarbon = dioxideCarbon;}
    public void setHumidity(String humidity) { this.humidity = humidity;}
    public void setHydrogen(String hydrogen) { this.hydrogen = hydrogen;}
    public void setToluene(String toluene) { this.toluene = toluene;}
    public void setUvLevel(String uvLevel) { this.uvLevel = uvLevel;}

}
