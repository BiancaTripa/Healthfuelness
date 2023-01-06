package com.example.healthfuelness

import User.getDate
import User.getUsername
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home_measurements.*

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

        //Selected date
        val tvDate = findViewById<TextView>(R.id.tv_selectedDate)
        val selectedDate = getDate()
        tvDate.text = selectedDate

        //Logout button
        val logoutNowButton = findViewById<TextView>(R.id.button_logout)

        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Water
        val incrementWaterButton = findViewById<ImageView>(R.id.button_increment)
        val decrementWaterButton = findViewById<ImageView>(R.id.button_decrement)
        val glassesOfWater = findViewById<TextView>(R.id.tv_water)

        incrementWaterButton.setOnClickListener {
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

        decrementWaterButton.setOnClickListener {
            //decrement the variable and display it
            if (glassesOfWater.text != "0") {
                currentWater -= 1
                glassesOfWater.text = currentWater.toString()
            }

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

        //Stress Level
        val outputForStressLevel = findViewById<TextView>(R.id.tv_stress_level)
        val stress1Button = findViewById<ImageView>(R.id.button_stress1)
        val stress2Button = findViewById<ImageView>(R.id.button_stress2)
        val stress3Button = findViewById<ImageView>(R.id.button_stress3)
        val stress4Button = findViewById<ImageView>(R.id.button_stress4)
        val stress5Button = findViewById<ImageView>(R.id.button_stress5)

        stress1Button.setOnClickListener {
            currentStressLevel = 1
            outputForStressLevel.text = currentStressLevel.toString()

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

        stress2Button.setOnClickListener {
            currentStressLevel = 2
            outputForStressLevel.text = currentStressLevel.toString()

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

        stress3Button.setOnClickListener {
            currentStressLevel = 3
            outputForStressLevel.text = currentStressLevel.toString()

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

        stress4Button.setOnClickListener {
            currentStressLevel = 4
            outputForStressLevel.text = currentStressLevel.toString()

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

        stress5Button.setOnClickListener {
            currentStressLevel = 5
            outputForStressLevel.text = currentStressLevel.toString()

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


        //Weight
        val incrementWeightButton = findViewById<ImageView>(R.id.button_increment_weight)
        val decrementWeightButton = findViewById<ImageView>(R.id.button_decrement_weight)
        val weight = findViewById<TextView>(R.id.tv_weight)

        incrementWeightButton.setOnClickListener {
            //increment the variable and display it
            currentWeight += 1
            weight.text = currentWeight.toString()

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

        decrementWeightButton.setOnClickListener {
            //decrement the variable and display it
            if (weight.text != "0") {
                currentWeight -= 1
                weight.text = currentWeight.toString()
            }

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
}