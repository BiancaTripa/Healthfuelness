package com.example.healthfuelness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.annotations.NotNull

class HomeActivity : AppCompatActivity() {

    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val date = findViewById<TextView>(R.id.tv_selectedDate)
        val goToDateButton = findViewById<TextView>(R.id.tv_btn_selectedDate)
        val calendarView = findViewById<CalendarView>(R.id.cv_calendar)

        calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            val month = month + 1
            val selectedDate = "$day/$month/$year"
            Log.d("date", selectedDate)
            date.text = selectedDate
        }

        goToDateButton.setOnClickListener {
            val intent = Intent(this, HomeMeasurementsActivity::class.java)
            //intent.putExtra("date", selectedDate)
            startActivity(intent)
        }

        val logoutNowButton = findViewById<TextView>(R.id.button_logout)

        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}