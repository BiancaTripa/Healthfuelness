package com.example.healthfuelness

import User.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
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
            date.text = "$dayAsString/$monthAsString/$year"
            dateToBeSaved = "$year/$monthAsString/$dayAsString"
            setDate(dateToBeSaved)
            if (dateToBeSaved == currentDate) {
                setCurrentDateOrNot(0)
                println("selected date: ${getCurrentDateOrNot()}")
            } else if (dateToBeSaved > currentDate){
                setCurrentDateOrNot(1)
                println("selected date: ${getCurrentDateOrNot()}")
            } else {
                setCurrentDateOrNot(-1)
                println("selected date: ${getCurrentDateOrNot()}")
            }
        }


        // go to measurement to see/add/update possible only if selected date is the current/previous one
        goToDateButton.setOnClickListener { //view ->
            if (getCurrentDateOrNot() != 1) {
                println("selected date goToDateButton: ${getCurrentDateOrNot()}")
                val intent = Intent(this, HomeMeasurementsActivity::class.java)
                startActivity(intent)
            } else {
                println("selected date goToDateButton: ${getCurrentDateOrNot()}")
                Toast.makeText(this, "Sorry. You can't predict the future and neither can we ", Toast.LENGTH_SHORT).show()
            }

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

        val GoToMapsPageButton = findViewById<ImageView>(R.id.button_map)

        GoToMapsPageButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
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