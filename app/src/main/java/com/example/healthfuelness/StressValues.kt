package com.example.healthfuelness

class StressValues (
    private var stressMin: Int = 100,
    private var stressMax: Int = 0,
    private var waterMin: Int = 100,
    private var waterMax: Int = 0,
    private var arrayWaterMin: Array<Int>,
    private var arrayWaterMax: Array<Int>,
){
    fun setStressMin(aux: Int) {this.stressMin = aux}
    fun getStressMin(): Int {return this.stressMin}
    fun setStressMax(aux: Int) {this.stressMax = aux}
    fun getStressMax(): Int {return this.stressMax}

    fun setWaterMin(aux: Int) {this.waterMin = aux}
    fun getWaterMin(): Int {return this.waterMin}
    fun setWaterMax(aux: Int) {this.waterMax = aux}
    fun getWaterMax(): Int {return this.waterMax}
    fun createArrayMinWithFirstValue(auxValue: Int){
        val aux = arrayOf(auxValue)
        this.arrayWaterMin = aux
    }
    fun addValueInArrayMin(aux: Int){
        this.arrayWaterMin += aux
    }
    fun createArrayMaxWithFirstValue(auxValue: Int){
        val aux = arrayOf(auxValue)
        this.arrayWaterMax = aux
    }
    fun addValueInArrayMax(aux: Int){
        this.arrayWaterMax += aux
    }
    fun makeMediaOfArrayMin(): Int{
        var sum = 0
        var index = 0
        for (x in this.arrayWaterMin) {
            sum += x
            index++
        }
        if (sum == 0) {
            return sum
        }
        return sum/index
    }
    fun makeMediaOfArrayMax(): Int{
        var sum = 0
        var index = 0
        for (x in this.arrayWaterMax) {
            sum += x
            index++
        }
        if (sum == 0) {
            return sum
        }
        return sum/index
    }
}