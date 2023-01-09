package com.example.healthfuelness

import User.*
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    private var currentSleep = 0
    private var currentWeight = 0
    private var currentShoulders = 0
    private var currentChest = 0
    private var currentWeist = 0
    private var currentUpperLeg = 0
    private var currentAnkle = 0
    private var currentHeight = 0
    private var currentTorso = 0
    private var currentLegs = 0

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
                        currentWater = snapshot.child("measurements").child(selectedDate).child("water").getValue(Int::class.java)!!
                        glassesOfWater.text = currentWater.toString()
                    } else { // if data not exits => add to database with default values
                        val measurements = Measurements(currentWater, currentStressLevel, currentSleep, currentWeight, currentShoulders, currentChest, currentWeist, currentUpperLeg, currentAnkle, currentHeight, currentTorso, currentLegs)
                        val measurementsValues = measurements.toMap()
                        databaseReference.child("users").child(username).child("measurements").child(getDate()).updateChildren(measurementsValues);
                        glassesOfWater.text = currentWater.toString()
                        outputForStressLevel.text = currentStressLevel.toString()
                        weight.text = currentWeight.toString()

                    }
                } else {// if data not exits => add to database with default values
                    val measurements = Measurements(currentWater, currentStressLevel, currentSleep, currentWeight, currentShoulders, currentChest, currentWeist, currentUpperLeg, currentAnkle, currentHeight, currentTorso, currentLegs)
                    val measurementsValues = measurements.toMap()
                    databaseReference.child("users").child(username).child("measurements").child(getDate()).updateChildren(measurementsValues);
                    glassesOfWater.text = currentWater.toString()
                    outputForStressLevel.text = currentStressLevel.toString()
                    weight.text = currentWeight.toString()
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

                //add to realtime database
                databaseReference.child("users").child(username).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val measurements = Measurements(currentWater, currentStressLevel, currentSleep, currentWeight, currentShoulders, currentChest, currentWeist, currentUpperLeg, currentAnkle, currentHeight, currentTorso, currentLegs)
                        val measurementsValues = measurements.toMap()
                        databaseReference.child("users").child(username).child("measurements").child(getDate()).updateChildren(measurementsValues);
                    }
                    override fun onCancelled(error: DatabaseError) { }
                })
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

                //add to realtime database
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val measurements = Measurements(
                                currentWater,
                                currentStressLevel,
                                currentSleep,
                                currentWeight,
                                currentShoulders,
                                currentChest,
                                currentWeist,
                                currentUpperLeg,
                                currentAnkle,
                                currentHeight,
                                currentTorso,
                                currentLegs
                            )
                            val measurementsValues = measurements.toMap()
                            databaseReference.child("users").child(username).child("measurements")
                                .child(getDate()).updateChildren(measurementsValues);
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
        }

        //Stress Level
        stress1Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 1
                outputForStressLevel.text = currentStressLevel.toString()

                //add to realtime database
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val measurements = Measurements(
                                currentWater,
                                currentStressLevel,
                                currentSleep,
                                currentWeight,
                                currentShoulders,
                                currentChest,
                                currentWeist,
                                currentUpperLeg,
                                currentAnkle,
                                currentHeight,
                                currentTorso,
                                currentLegs
                            )
                            val measurementsValues = measurements.toMap()
                            databaseReference.child("users").child(username).child("measurements")
                                .child(getDate()).updateChildren(measurementsValues);
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
        }

        stress2Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 2
                outputForStressLevel.text = currentStressLevel.toString()

                //add to realtime database
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val measurements = Measurements(
                                currentWater,
                                currentStressLevel,
                                currentSleep,
                                currentWeight,
                                currentShoulders,
                                currentChest,
                                currentWeist,
                                currentUpperLeg,
                                currentAnkle,
                                currentHeight,
                                currentTorso,
                                currentLegs
                            )
                            val measurementsValues = measurements.toMap()
                            databaseReference.child("users").child(username).child("measurements")
                                .child(getDate()).updateChildren(measurementsValues);
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
        }

        stress3Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 3
                outputForStressLevel.text = currentStressLevel.toString()

                //add to realtime database
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val measurements = Measurements(
                                currentWater,
                                currentStressLevel,
                                currentSleep,
                                currentWeight,
                                currentShoulders,
                                currentChest,
                                currentWeist,
                                currentUpperLeg,
                                currentAnkle,
                                currentHeight,
                                currentTorso,
                                currentLegs
                            )
                            val measurementsValues = measurements.toMap()
                            databaseReference.child("users").child(username).child("measurements")
                                .child(getDate()).updateChildren(measurementsValues);
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
        }

        stress4Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 4
                outputForStressLevel.text = currentStressLevel.toString()

                //add to realtime database
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val measurements = Measurements(
                                currentWater,
                                currentStressLevel,
                                currentSleep,
                                currentWeight,
                                currentShoulders,
                                currentChest,
                                currentWeist,
                                currentUpperLeg,
                                currentAnkle,
                                currentHeight,
                                currentTorso,
                                currentLegs
                            )
                            val measurementsValues = measurements.toMap()
                            databaseReference.child("users").child(username).child("measurements")
                                .child(getDate()).updateChildren(measurementsValues);
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
        }

        stress5Button.setOnClickListener {
            // add/update the measurements can be possible only if the current date is selected
            if (getCurrentDateOrNot() == 0) {
                currentStressLevel = 5
                outputForStressLevel.text = currentStressLevel.toString()

                //add to realtime database
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val measurements = Measurements(
                                currentWater,
                                currentStressLevel,
                                currentSleep,
                                currentWeight,
                                currentShoulders,
                                currentChest,
                                currentWeist,
                                currentUpperLeg,
                                currentAnkle,
                                currentHeight,
                                currentTorso,
                                currentLegs
                            )
                            val measurementsValues = measurements.toMap()
                            databaseReference.child("users").child(username).child("measurements")
                                .child(getDate()).updateChildren(measurementsValues);
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
        }

        //Sleep
        btn_sleep.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hoursleep, minutesleep ->

                cal.set(Calendar.HOUR_OF_DAY, hoursleep)
                cal.set(Calendar.MINUTE, minutesleep)
                btn_sleep.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        btn_wakeup.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hourwakeup, minutewakeup ->

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

                //add to realtime database
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val measurements = Measurements(
                                currentWater,
                                currentStressLevel,
                                currentSleep,
                                currentWeight,
                                currentShoulders,
                                currentChest,
                                currentWeist,
                                currentUpperLeg,
                                currentAnkle,
                                currentHeight,
                                currentTorso,
                                currentLegs
                            )
                            val measurementsValues = measurements.toMap()
                            databaseReference.child("users").child(username).child("measurements")
                                .child(getDate()).updateChildren(measurementsValues);
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
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

                //add to realtime database
                databaseReference.child("users").child(username)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val measurements = Measurements(
                                currentWater,
                                currentStressLevel,
                                currentSleep,
                                currentWeight,
                                currentShoulders,
                                currentChest,
                                currentWeist,
                                currentUpperLeg,
                                currentAnkle,
                                currentHeight,
                                currentTorso,
                                currentLegs
                            )
                            val measurementsValues = measurements.toMap()
                            databaseReference.child("users").child(username).child("measurements")
                                .child(getDate()).updateChildren(measurementsValues);
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
        }
    }

}