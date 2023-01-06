package com.example.healthfuelness

import User.setDate
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import org.jetbrains.annotations.NotNull
import androidx.navigation.Navigation

class HomeActivity : AppCompatActivity() {

    private lateinit var selectedDate: String
    private lateinit var dateToBeSaved: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val date = findViewById<TextView>(R.id.tv_selectedDate)
        val goToDateButton = findViewById<TextView>(R.id.tv_btn_selectedDate)
        val calendarView = findViewById<CalendarView>(R.id.cv_calendar)

        calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            val month = month + 1
            selectedDate = "$day/$month/$year"
            dateToBeSaved = "$year/$month/$day"
            Log.d("selectedDate", selectedDate)
            date.text = selectedDate
            setDate(dateToBeSaved)
        }


        goToDateButton.setOnClickListener { //view ->
            val intent = Intent(this, HomeMeasurementsActivity::class.java)
            startActivity(intent)

            //val action = HomeActivityDirections.actionSelectedDate(selectedDate)
            //view.findNavController().navigate(action)

            //val bundle = bundleOf("selectedDate" to selectedDate)
            //view.findNavController().navigate(R.id.action_selectedDate, bundle)
        }

        val logoutNowButton = findViewById<TextView>(R.id.button_logout)

        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}