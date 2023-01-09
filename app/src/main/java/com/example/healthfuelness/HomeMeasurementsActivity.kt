package com.example.healthfuelness

import User.*
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home_measurements.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class HomeMeasurementsActivity : AppCompatActivity() {

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")


    //private val args: HomeActivityArgs by navArgs()
    private var currentWater = 0
    private var currentStressLevel = 0
    private var currentWeight = 0
    private var currentShoulders = 0
    private var currentChest = 0
    private var currentWaist = 0
    private var currentUpperLeg = 0
    private var currentAnkle = 0
    private var currentHeight = 0
    private var currentTorso = 0
    private var currentLegs = 0

    private var currentHourSleep = 0
    private var currentMinuteSleep = 0
    private var currentHourWakeup = 0
    private var currentMinuteWakeup = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_measurements)

        val username = getUsername()
        val selectedDate = getDate()

        val tvDate = findViewById<TextView>(R.id.tv_selectedDate)

        val incrementWaterButton = findViewById<ImageView>(R.id.button_increment)
        val decrementWaterButton = findViewById<ImageView>(R.id.button_decrement)
        val glassesOfWater = findViewById<TextView>(R.id.tv_water)

        val outputForStressLevel = findViewById<TextView>(R.id.tv_stress_level)
        val stress1Button = findViewById<ImageView>(R.id.button_stress1)
        val stress2Button = findViewById<ImageView>(R.id.button_stress2)
        val stress3Button = findViewById<ImageView>(R.id.button_stress3)
        val stress4Button = findViewById<ImageView>(R.id.button_stress4)
        val stress5Button = findViewById<ImageView>(R.id.button_stress5)

        val weight = findViewById<TextView>(R.id.tv_weight)
        val incrementWeightButton = findViewById<ImageView>(R.id.button_increment_weight)
        val decrementWeightButton = findViewById<ImageView>(R.id.button_decrement_weight)

        val height = findViewById<EditText>(R.id.tv_height)
        val torso = findViewById<EditText>(R.id.tv_torso)
        val legs = findViewById<EditText>(R.id.tv_legs)
        val shoulders = findViewById<EditText>(R.id.tv_shoulders)
        val chest = findViewById<EditText>(R.id.tv_chest)
        val waist  = findViewById<EditText>(R.id.tv_waist)
        val upperLeg = findViewById<EditText>(R.id.tv_upperleg)
        val ankle = findViewById<EditText>(R.id.tv_ankle)

        //get the measurements from database
        //if selected date is before current date => the measurements will be only displayed
        //if selected date is the current date => the measurements will be updated on the corresponding actions
        databaseReference.child("users").child(username).addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                //check if measurements exists in firebase database
                if (snapshot.hasChild("measurements")) {

                    //check if measurements for current date exists
                    if (snapshot.child("measurements").hasChild(selectedDate)){
                        //water
                        currentWater = snapshot.child("measurements").child(selectedDate).child("water").getValue(Int::class.java)!!
                        glassesOfWater.text = currentWater.toString()
                        //stress level
                        currentStressLevel = snapshot.child("measurements").child(selectedDate).child("stressLevel").getValue(Int::class.java)!!
                        outputForStressLevel.text = currentStressLevel.toString()
                        //sleep

                        /*
                        currentHourSleep = snapshot.child("measurements").child(selectedDate).child("hourSleep").getValue(Int::class.java)!!
                        currentMinuteSleep = snapshot.child("measurements").child(selectedDate).child("minuteSleep").getValue(Int::class.java)!!
                        val calSleep = Calendar.getInstance()
                        calSleep.set(Calendar.HOUR_OF_DAY, currentHourSleep)
                        calSleep.set(Calendar.MINUTE, currentMinuteSleep)
                        btn_sleep.text = SimpleDateFormat("HH:mm").format(calSleep.time)

                        currentHourWakeup = snapshot.child("measurements").child(selectedDate).child("hourWakeup").getValue(Int::class.java)!!
                        currentMinuteWakeup = snapshot.child("measurements").child(selectedDate).child("minuteWakeup").getValue(Int::class.java)!!
                        val calWakeup = Calendar.getInstance()
                        calWakeup.set(Calendar.HOUR_OF_DAY, currentHourWakeup)
                        calWakeup.set(Calendar.MINUTE, currentMinuteWakeup)
                        btn_wakeup.text = SimpleDateFormat("HH:mm").format(calWakeup.time)

                         */

                        //weight
                        currentWeight = snapshot.child("measurements").child(selectedDate).child("weight").getValue(Int::class.java)!!
                        weight.text = currentWeight.toString()
                        //body:
                        //height
                        currentHeight= snapshot.child("measurements").child(selectedDate).child("height").getValue(Int::class.java)!!
                        height.hint = currentHeight.toString()
                        //torso
                        currentTorso= snapshot.child("measurements").child(selectedDate).child("torso").getValue(Int::class.java)!!
                        torso.hint = currentTorso.toString()
                        //legs
                        currentLegs= snapshot.child("measurements").child(selectedDate).child("legs").getValue(Int::class.java)!!
                        legs.hint = currentLegs.toString()
                        //shoulders
                        currentShoulders= snapshot.child("measurements").child(selectedDate).child("shoulders").getValue(Int::class.java)!!
                        shoulders.hint = currentShoulders.toString()
                        //chest
                        currentChest= snapshot.child("measurements").child(selectedDate).child("chest").getValue(Int::class.java)!!
                        chest.hint = currentChest.toString()
                        //waist
                        currentWaist= snapshot.child("measurements").child(selectedDate).child("waist").getValue(Int::class.java)!!
                        waist.hint = currentWaist.toString()
                        //upperLeg
                        currentUpperLeg= snapshot.child("measurements").child(selectedDate).child("upperLeg").getValue(Int::class.java)!!
                        upperLeg.hint = currentUpperLeg.toString()
                        //ankle
                        currentAnkle= snapshot.child("measurements").child(selectedDate).child("ankle").getValue(Int::class.java)!!
                        ankle.hint = currentAnkle.toString()

                    } else { // if data not exits => add to database with default values
                        val measurements = Measurements(currentWater, currentStressLevel, currentHourSleep, currentMinuteSleep, currentHourWakeup, currentMinuteWakeup, currentWeight, currentShoulders, currentChest, currentWaist, currentUpperLeg, currentAnkle, currentHeight, currentTorso, currentLegs)
                        val measurementsValues = measurements.toMap()
                        databaseReference.child("users").child(username).child("measurements").child(getDate()).updateChildren(measurementsValues);
                        //water
                        glassesOfWater.text = currentWater.toString()
                        //stress level
                        outputForStressLevel.text = currentStressLevel.toString()
                        //sleep

                        /*
                        val calSleep = Calendar.getInstance()
                        calSleep.set(Calendar.HOUR_OF_DAY, currentHourSleep)
                        calSleep.set(Calendar.MINUTE, currentMinuteSleep)
                        btn_sleep.text = SimpleDateFormat("HH:mm").format(calSleep.time)
                        val calWakeup = Calendar.getInstance()
                        calWakeup.set(Calendar.HOUR_OF_DAY, currentHourWakeup)
                        calWakeup.set(Calendar.MINUTE, currentMinuteWakeup)
                        btn_wakeup.text = SimpleDateFormat("HH:mm").format(calWakeup.time)
                         */

                        //weight
                        weight.hint = currentWeight.toString()
                        //body:
                        //height
                        height.hint = currentHeight.toString()
                        //torso
                        torso.hint = currentTorso.toString()
                        //legs
                        legs.hint = currentLegs.toString()
                        //shoulders
                        shoulders.hint = currentShoulders.toString()
                        //chest
                        chest.hint = currentChest.toString()
                        //waist
                        waist.hint = currentWaist.toString()
                        //upperLeg
                        upperLeg.hint = currentUpperLeg.toString()
                        //ankle
                        ankle.hint = currentAnkle.toString()

                    }
                } else {// if data not exits => add to database with default values
                    val measurements = Measurements(currentWater, currentStressLevel, currentHourSleep, currentMinuteSleep, currentHourWakeup, currentMinuteWakeup, currentWeight, currentShoulders, currentChest, currentWaist, currentUpperLeg, currentAnkle, currentHeight, currentTorso, currentLegs)
                    val measurementsValues = measurements.toMap()
                    databaseReference.child("users").child(username).child("measurements").child(getDate()).updateChildren(measurementsValues);
                    //water
                    glassesOfWater.text = currentWater.toString()
                    //stress level
                    outputForStressLevel.text = currentStressLevel.toString()
                    //sleep

                    /*
                    val calSleep = Calendar.getInstance()
                    calSleep.set(Calendar.HOUR_OF_DAY, currentHourSleep)
                    calSleep.set(Calendar.MINUTE, currentMinuteSleep)
                    btn_sleep.text = SimpleDateFormat("HH:mm").format(calSleep.time)
                    val calWakeup = Calendar.getInstance()
                    calWakeup.set(Calendar.HOUR_OF_DAY, currentHourWakeup)
                    calWakeup.set(Calendar.MINUTE, currentMinuteWakeup)
                    btn_wakeup.text = SimpleDateFormat("HH:mm").format(calWakeup.time)
                     */

                    //weight
                    weight.text = currentWeight.toString()
                    //body:
                    //height
                    height.hint = currentHeight.toString()
                    //torso
                    torso.hint = currentTorso.toString()
                    //legs
                    legs.hint = currentLegs.toString()
                    //shoulders
                    shoulders.hint = currentShoulders.toString()
                    //chest
                    chest.hint = currentChest.toString()
                    //waist
                    waist.hint = currentWaist.toString()
                    //upperLeg
                    upperLeg.hint = currentUpperLeg.toString()
                    //ankle
                    ankle.hint = currentAnkle.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("The read failed: " + error.code);
            }
        })

        //Selected date
        tvDate.text = selectedDate

        //Logout button
        val logoutNowButton = findViewById<TextView>(R.id.button_logout)

        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Water
        incrementWaterButton.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                //increment the variable and display it
                currentWater += 1
                glassesOfWater.text = currentWater.toString()
            }
        }

        decrementWaterButton.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                //decrement the variable and display it
                if (glassesOfWater.text != "0") {
                    currentWater -= 1
                    glassesOfWater.text = currentWater.toString()
                }
            }
        }

        //Stress Level
        stress1Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 1
                outputForStressLevel.text = currentStressLevel.toString()
            }
        }

        stress2Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 2
                outputForStressLevel.text = currentStressLevel.toString()
            }
        }

        stress3Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 3
                outputForStressLevel.text = currentStressLevel.toString()
            }
        }

        stress4Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 4
                outputForStressLevel.text = currentStressLevel.toString()
            }
        }

        stress5Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 5
                outputForStressLevel.text = currentStressLevel.toString()
            }
        }

        //Sleep
        btn_sleep.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hoursleep, minutesleep ->

                currentHourSleep = hoursleep
                currentMinuteSleep = minutesleep
                cal.set(Calendar.HOUR_OF_DAY, hoursleep)
                cal.set(Calendar.MINUTE, minutesleep)
                btn_sleep.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        btn_wakeup.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hourwakeup, minutewakeup ->

                currentHourWakeup = hourwakeup
                currentMinuteWakeup = minutewakeup
                cal.set(Calendar.HOUR_OF_DAY, hourwakeup)
                cal.set(Calendar.MINUTE, minutewakeup)
                btn_wakeup.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }



        //Weight
        incrementWeightButton.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                //increment the variable and display it
                currentWeight += 1
                weight.text = currentWeight.toString()
            }
        }

        decrementWeightButton.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                //decrement the variable and display it
                if (weight.text != "0") {
                    currentWeight -= 1
                    weight.text = currentWeight.toString()
                }
            }
        }

        //Home button
        val homeButton = findViewById<ImageView>(R.id.button_homemeasurements)

        homeButton.setOnClickListener {
            if (getCurrentDateOrNot() == 0) {
                //get data from EditTexts into String variables
                //body:
                //height
                val heightTxt = height.text.toString()
                currentHeight = if (heightTxt.isEmpty()) {
                    currentHeight
                } else {
                    heightTxt.toInt()
                }
                //torso
                val torsoTxt = torso.text.toString()
                currentTorso = if (torsoTxt.isEmpty()) {
                    currentTorso
                } else {
                    torsoTxt.toInt()
                }
                //legs
                val legsTxt = legs.text.toString()
                currentLegs = if (legsTxt.isEmpty()) {
                    currentLegs
                } else {
                    legsTxt.toInt()
                }
                //shoulders
                val shouldersTxt = shoulders.text.toString()
                currentShoulders = if (shouldersTxt.isEmpty()) {
                    currentShoulders
                } else {
                    shouldersTxt.toInt()
                }
                //chest
                val chestTxt = chest.text.toString()
                currentChest = if (chestTxt.isEmpty()) {
                    currentChest
                } else {
                    chestTxt.toInt()
                }
                //waist
                val waistTxt = waist.text.toString()
                currentWaist = if (waistTxt.isEmpty()) {
                    currentWaist
                } else {
                    waistTxt.toInt()
                }
                //upperLeg
                val upperLegTxt = upperLeg.text.toString()
                currentUpperLeg = if (upperLegTxt.isEmpty()) {
                    currentUpperLeg
                } else {
                    upperLegTxt.toInt()
                }
                //ankle
                val ankleTxt = ankle.text.toString()
                currentAnkle = if (ankleTxt.isEmpty()) {
                    currentAnkle
                } else {
                    ankleTxt.toInt()
                }

                //add to realtime database
                val getContext = this
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("height").setValue(currentHeight)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("torso").setValue(currentTorso)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("legs").setValue(currentLegs)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("shoulders").setValue(currentShoulders)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("chest").setValue(currentChest)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("waist").setValue(currentWaist)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("upperLeg").setValue(currentUpperLeg)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("ankle").setValue(currentAnkle)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("water").setValue(currentWater)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("stressLevel")
                                .setValue(currentStressLevel)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("weight").setValue(currentWeight)

                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("hourSleep").setValue(currentHourSleep)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("minuteSleep").setValue(currentMinuteSleep)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("hourWakeup").setValue(currentHourWakeup)
                            databaseReference.child("users").child(username).child("measurements")
                                .child(selectedDate).child("minuteWakeup").setValue(currentMinuteWakeup)

                            Toast.makeText(
                                getContext,
                                "Measurements added successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            //go to home page
                            val intent = Intent(getContext, HomeActivity::class.java)
                            startActivity(intent)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            println("The write failed: " + error.code);
                        }
                    })
            } else {
                //go to home page
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

    }

}