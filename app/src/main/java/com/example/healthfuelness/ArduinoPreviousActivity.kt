package com.example.healthfuelness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ArduinoPreviousActivity: AppCompatActivity() {
    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    private val username = User.getUsername()
    private val selectedDate = User.getDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arduino_previous)

        //declaration for buttons and text views from xml
        val buttonBack = findViewById<TextView>(R.id.button_back)
        val buttonMoreDetails = findViewById<Button>(R.id.button_more_details)
        val buttonGoHome = findViewById<ImageView>(R.id.button_home_page)
        val textViewSelectedDate = findViewById<TextView>(R.id.tv_selectedDate)
        val textViewMessageDate = findViewById<TextView>(R.id.tv_message)
        val textViewTemperatureMin = findViewById<TextView>(R.id.tv_temperature_min)
        val textViewTemperatureMax = findViewById<TextView>(R.id.tv_temperature_max)
        val textViewHumidityMin = findViewById<TextView>(R.id.tv_humidity_min)
        val textViewHumidityMax = findViewById<TextView>(R.id.tv_humidity_max)
        val textViewUvLevelMin = findViewById<TextView>(R.id.tv_uv_level_min)
        val textViewUvLevelMax = findViewById<TextView>(R.id.tv_uv_level_max)
        val textViewAirQualityStatusMin = findViewById<TextView>(R.id.tv_air_quality_status_min)
        val textViewAirQualityStatusMax = findViewById<TextView>(R.id.tv_air_quality_status_max)

        //Selected date
        textViewSelectedDate.text = selectedDate

        //back button
        buttonBack.setOnClickListener {
            val intent = Intent(this, HomeMeasurementsActivity::class.java)
            startActivity(intent)
        }

        //home button
        buttonGoHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //go see more details button
        buttonMoreDetails.setOnClickListener {
            val intent = Intent(this, MoreDetailsActivity::class.java)
            startActivity(intent)
        }

        //check if data from arduino in the current date exists in firebase realtime database
        databaseReference.child("users").child(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("dataFromArduino")) {
                        if (snapshot.child("dataFromArduino").hasChild(selectedDate)) { // data exist for this user, in this date

                            textViewMessageDate.text = "For more details, press the bellow button :) "
                            markButtonEnable(buttonMoreDetails)

                            //temperature
                            textViewTemperatureMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("temperatureMin").value.toString()
                            textViewTemperatureMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("temperatureMax").value.toString()

                            //humidity
                            textViewHumidityMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("humidityMin").value.toString()
                            textViewHumidityMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("humidityMax").value.toString()

                            //UV Level
                            textViewUvLevelMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("uvLevelMin").value.toString()
                            textViewUvLevelMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("uvLevelMax").value.toString()

                            //Air Quality
                            textViewAirQualityStatusMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("airQualityMin").value.toString()
                            textViewAirQualityStatusMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("airQualityMax").value.toString()


                        } else { // dataFromArduino in selected date not exits for this user
                            textViewMessageDate.text = "We are sorry, but for this date the data are not available :("
                            markButtonDisable(buttonMoreDetails)
                        }
                    } else { // dataFromArduino not exits for this user
                        textViewMessageDate.text = "We are sorry, but for this date the data are not available :("
                        markButtonDisable(buttonMoreDetails)
                    }
                }

                override fun onCancelled(firebaseError: DatabaseError) {
                    println("The read failed: " + firebaseError.message)
                }
            })
    }

    fun markButtonDisable(button: Button) {
        button.isEnabled = false
        button.isClickable = false
        button.setTextColor(ContextCompat.getColor(this, R.color.white))
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
    }

    fun markButtonEnable(button: Button) {
        button.isEnabled = true
        button.isClickable = true
        button.setTextColor(ContextCompat.getColor(this, R.color.black))
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }
}