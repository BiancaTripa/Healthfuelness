package com.example.healthfuelness

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class DataToBeSaved(
    private var temperatureMin: String,
    private var temperatureMax: String,
    private var humidityMin: String,
    private var humidityMax: String,
    private var uvLevelMin: String,
    private var uvLevelMax: String,
    private var airQualityMin: String,
    private var airQualityMax: String,
    private var tolueneMin: String,
    private var tolueneMax: String,
    private var acetoneMin: String,
    private var acetoneMax: String,
    private var ammoniaMin: String,
    private var ammoniaMax: String,
    private var alcoholMin: String,
    private var alcoholMax: String,
    private var dioxideCarbonMin: String,
    private var dioxideCarbonMax: String,
    private var hydrogenMin: String,
    private var hydrogenMax: String
){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "temperatureMin" to temperatureMin,
            "temperatureMax" to temperatureMax,
            "humidityMin" to humidityMin,
            "humidityMax" to humidityMax,
            "uvLevelMin" to uvLevelMin,
            "uvLevelMax" to uvLevelMax,
            "airQualityMin" to airQualityMin,
            "airQualityMax" to airQualityMax,
            "tolueneMin" to tolueneMin,
            "tolueneMax" to tolueneMax,
            "acetoneMin" to acetoneMin,
            "acetoneMax" to acetoneMax,
            "ammoniaMin" to ammoniaMin,
            "ammoniaMax" to ammoniaMax,
            "alcoholMin" to alcoholMin,
            "alcoholMax" to alcoholMax,
            "dioxideCarbonMin" to dioxideCarbonMin,
            "dioxideCarbonMax" to dioxideCarbonMax,
            "hydrogenMin" to hydrogenMin,
            "hydrogenMax" to hydrogenMax
        )
    }

    fun getTemperatureMin(): String {
        return this.temperatureMin
    }
    fun setTemperatureMin(aux : String){
        this.temperatureMin = aux
    }
    fun getTemperatureMax(): String {
        return this.temperatureMax
    }
    fun setTemperatureMax(aux : String){
        this.temperatureMax = aux
    }

    fun getHumidityMin(): String {
        return this.humidityMin
    }
    fun setHumidityMin(aux : String){
        this.humidityMin = aux
    }
    fun getHumidityMax(): String {
        return this.humidityMax
    }
    fun setHumidityMax(aux : String){
        this.humidityMax = aux
    }

    fun getUvLevelMin(): String {
        return this.uvLevelMin
    }
    fun setUvLevelMin(aux : String){
        this.uvLevelMin = aux
    }
    fun getUvLevelMax(): String {
        return this.uvLevelMax
    }
    fun setUvLevelMax(aux : String){
        this.uvLevelMax = aux
    }

    fun getAirQualityMin(): String {
        return this.airQualityMin
    }
    fun setAirQualityMin(aux : String){
        this.airQualityMin = aux
    }
    fun getAirQualityMax(): String {
        return this.airQualityMax
    }
    fun setAirQualityMax(aux : String){
        this.airQualityMax = aux
    }

    fun getTolueneMin(): String {
        return this.tolueneMin
    }
    fun setTolueneMin(aux : String){
        this.tolueneMin = aux
    }
    fun getTolueneMax(): String {
        return this.tolueneMax
    }
    fun setTolueneMax(aux : String){
        this.tolueneMax = aux
    }

    fun getAcetoneMin(): String {
        return this.acetoneMin
    }
    fun setAcetoneMin(aux : String){
        this.acetoneMin = aux
    }
    fun getAcetoneMax(): String {
        return this.acetoneMax
    }
    fun setAcetoneMax(aux : String){
        this.acetoneMax = aux
    }

    fun getAmmoniaMin(): String {
        return this.ammoniaMin
    }
    fun setAmmoniaMin(aux : String){
        this.ammoniaMin = aux
    }
    fun getAmmoniaMax(): String {
        return this.ammoniaMax
    }
    fun setAmmoniaMax(aux : String){
        this.ammoniaMax = aux
    }

    fun getAlcoholMin(): String {
        return this.alcoholMin
    }
    fun setAlcoholMin(aux : String){
        this.alcoholMin = aux
    }
    fun getAlcoholMax(): String {
        return this.alcoholMax
    }
    fun setAlcoholMax(aux : String){
        this.alcoholMax = aux
    }

    fun getDioxideCarbonMin(): String {
        return this.dioxideCarbonMin
    }
    fun setDioxideCarbonMin(aux : String){
        this.dioxideCarbonMin = aux
    }
    fun getDioxideCarbonMax(): String {
        return this.dioxideCarbonMax
    }
    fun setDioxideCarbonMax(aux : String){
        this.dioxideCarbonMax = aux
    }

    fun getHydrogenMin(): String {
        return this.hydrogenMin
    }
    fun setHydrogenMin(aux : String){
        this.hydrogenMin = aux
    }
    fun getHydrogenMax(): String {
        return this.hydrogenMax
    }
    fun setHydrogenMax(aux : String){
        this.hydrogenMax = aux
    }
}