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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    private lateinit var dateToBeSaved: String

    private val date = getCurrentDateTime()
    private val currentDate = date.toString("yyyy/MM/dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val date = findViewById<TextView>(R.id.tv_selectedDate)
        val goToDateButton = findViewById<TextView>(R.id.tv_btn_selectedDate)
        val calendarView = findViewById<CalendarView>(R.id.cv_calendar)
        val quoteText = findViewById<TextView>(R.id.tv_daily_quote)
        val logoutNowButton = findViewById<TextView>(R.id.button_logout)
        val galleryButton = findViewById<ImageView>(R.id.button_happy)
        val newQuoteButton = findViewById<ImageView>(R.id.button_home)

        //random quote from database everytime you go to home page
        val getContext = this
        databaseReference.child("quote").addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val randomId = (1 until 11).random()

                //check if quote is exist in firebase database
                if (snapshot.hasChild(randomId.toString())) {

                    val getQuote = snapshot.child(randomId.toString()).getValue(String::class.java)
                    quoteText.text = getQuote

                } else {
                    quoteText.text = "Some went wrong with quote"
                    Toast.makeText(getContext, "Wrong quote id", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("The read failed: " + error.code);
            }
        })

        //calendar
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

        //go to logout
        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //go to home
        newQuoteButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //go to gallery
        galleryButton.setOnClickListener {
            val intent = Intent(this, ArduinoActivity::class.java)
            startActivity(intent)
        }
        
        //go to maps
        val GoToMapsPageButton = findViewById<ImageView>(R.id.button_map)

        GoToMapsPageButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

}