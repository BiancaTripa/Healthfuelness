package com.example.healthfuelness

class Previous5Values(

private var current: Float,
private var previous1: Float,
private var previous2: Float,
private var previous3: Float,
private var previous4: Float
) {
    fun setCurrent(aux: Float) {this.current = aux}
    fun getCurrent(): Float {return this.current}

    fun setPrevious1(aux: Float) {this.previous1 = aux}
    fun getPrevious1(): Float {return this.previous1}

    fun setPrevious2(aux: Float) {this.previous2 = aux}
    fun getPrevious2(): Float {return this.previous2}

    fun setPrevious3(aux: Float) {this.previous3 = aux}
    fun getPrevious3(): Float {return this.previous3}

    fun setPrevious4(aux: Float) {this.previous4 = aux}
    fun getPrevious4(): Float {return this.previous4}
}