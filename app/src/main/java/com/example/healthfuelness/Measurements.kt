package com.example.healthfuelness

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Measurements(
    var water: Int = 0,
    var stressLevel: Int = 3,
    var hourSleep: Int = 0,
    var minuteSleep: Int = 0,
    var hourWakeup: Int = 0,
    var minuteWakeup: Int = 0,
    var weight: Int = 0,
    var shoulders: Int = 0,
    var chest: Int = 0,
    var waist: Int = 0,
    var upperLeg: Int = 0,
    var ankle: Int = 0,
    var height: Int = 0,
    var torso: Int = 0,
    var legs: Int = 0
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "water" to water,
            "stressLevel" to stressLevel,
            "hourSleep" to hourSleep,
            "minuteSleep" to minuteSleep,
            "hourWakeup" to hourWakeup,
            "minuteWakeup" to minuteWakeup,
            "weight" to weight,
            "shoulders" to shoulders,
            "chest" to chest,
            "waist" to waist,
            "upperLeg" to upperLeg,
            "ankle" to ankle,
            "height" to height,
            "torso" to torso,
            "legs" to legs
        )
    }
}