package com.example.healthfuelness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeMeasurementsActivity : AppCompatActivity() {

    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_measurements)

        selectedDate = intent.getStringExtra("date").toString()
    }
}