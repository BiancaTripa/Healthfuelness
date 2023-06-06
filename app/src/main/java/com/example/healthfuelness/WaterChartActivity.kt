package com.example.healthfuelness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.db.williamchart.view.BarChartView

class WaterChartActivity : AppCompatActivity() {

    companion object {

        private val barSet = listOf(
            "JAN" to 4F,
            "FEB" to 7F,
            "MAR" to 2F,
            "MAY" to 2.3F,
            "APR" to 5F,
            "JUN" to 4F
        )

        //private val horizontalBarSet = listOf(
           // "PORRO" to 5F,
           // "FUSCE" to 6.4F,
           // "EGET" to 3F
       // )
        private const val animationDuration = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_chart)

        val barchartWater = findViewById<BarChartView>(R.id.barChartVer)

        barchartWater.animation.duration = animationDuration
        barchartWater.animate(barSet)
    }
}