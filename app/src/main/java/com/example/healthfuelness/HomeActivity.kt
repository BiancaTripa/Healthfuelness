package com.example.healthfuelness

import User.setCurrentDateOrNot
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
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var dateToBeSaved: String

    private val date = getCurrentDateTime()
    private val currentDate = date.toString("yyyy/MM/dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val date = findViewById<TextView>(R.id.tv_selectedDate)
        val goToDateButton = findViewById<TextView>(R.id.tv_btn_selectedDate)
        val calendarView = findViewById<CalendarView>(R.id.cv_calendar)

        calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            val month = month + 1
            var monthAsString = "$month"
            var dayAsString = "$day"

            if (month < 10) {
                monthAsString = "0$month"
            }
            if (day < 10) {
                dayAsString = "0$day"
            }
            date.text = "$day/$monthAsString/$dayAsString"
            dateToBeSaved = "$year/$monthAsString/$dayAsString"
            setDate(dateToBeSaved)
            if (dateToBeSaved == currentDate) {
                setCurrentDateOrNot(0)
            } else if (dateToBeSaved > currentDate){
                setCurrentDateOrNot(1)
            } else {
                setCurrentDateOrNot(-1)
            }
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

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

}