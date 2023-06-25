package com.example.healthfuelness

import com.example.healthfuelness.User.setUsername
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.healthfuelness.User.getUsername
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfileActivity : AppCompatActivity() {

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    private var currentHeight = 100
    private var currentAge: Int = 18
    private var currentWeight = 35
    private var currentFullname = " "
    private var currentPassword = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val username = getUsername()
        val fullName = findViewById<EditText>(R.id.fullname)
        val age = findViewById<EditText>(R.id.age)
        val height = findViewById<EditText>(R.id.tv_height)
        val weight = findViewById<EditText>(R.id.tv_weight)
        val changePassword = findViewById<EditText>(R.id.change_password)
        val conPassword = findViewById<EditText>(R.id.conPassword)
        val saveBtn = findViewById<TextView>(R.id.button_saveP)
        val discardButton = findViewById<TextView>(R.id.button_discard)

        //get the details from database
        databaseReference.child("users").child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //check if measurements exists in firebase database

                if (snapshot.hasChild("fullname")) { //fullname exists in database
                    currentFullname = snapshot.child("fullname").value.toString()
                    fullName.hint = currentFullname
                } else {
                    //fullneame doesn`t exist in database
                    fullName.hint = currentFullname
                }
/*
                if (snapshot.hasChild("age")) { //age exists in database
                    currentAge = snapshot.child("age").getValue(Int::class.java)!!
                    age.hint = currentAge.toString()
                } else {
                    //age doesn`t exist in database
                    age.hint = currentAge.toString()
                }

                if (snapshot.hasChild("height")) { //height exists in database
                    currentHeight = snapshot.child("height").getValue(Int::class.java)!!
                    height.hint = currentHeight.toString()
                } else {
                    //height doesn`t exist in database
                    height.hint = currentHeight.toString()
                }

                if (snapshot.hasChild("weight")) { //weight exists in database
                    currentWeight = snapshot.child("weight").getValue(Int::class.java)!!
                    weight.hint = currentWeight.toString()
                } else {
                    //weight doesn`t exist in database
                    weight.hint = currentWeight.toString()
                }

                if (snapshot.hasChild("password")) { //password exists in database
                    currentPassword = snapshot.child("password").value.toString()
                    changePassword.hint = currentPassword
                } else {
                    //password doesn`t exist in database
                    changePassword.hint = currentPassword
                }*/

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        //send to database
        saveBtn.setOnClickListener {
        //get data from EditTexts into String variables
            val fullNameTxt = fullName.text.toString()
            val passwordTxt = changePassword.text.toString()
            val conPasswordTxt = conPassword.text.toString()
            val ageTxt = age.text.toString()
            val heightTxt = height.text.toString()
            val weightTxt = weight.text.toString()

            //check if user fills all the fields before sending data to firebase
            /*if (fullNameTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty() || ageTxt.isEmpty() || heightTxt.isEmpty() || weightTxt.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }*/

            //check if passwords match
            if (passwordTxt != conPasswordTxt) {
                Toast.makeText(this, "Passwords are not matching", Toast.LENGTH_SHORT).show()
            }

            //send data to firebase RealTime Database
            else {
                val getContext = this
                databaseReference.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!ageTxt.isEmpty()){
                        databaseReference.child("users").child(username).child("age").setValue(ageTxt)
                    }
                    if (!weightTxt.isEmpty()) {
                        databaseReference.child("users").child(username).child("weight")
                            .setValue(weightTxt)
                    }
                    if (!heightTxt.isEmpty()) {
                        databaseReference.child("users").child(username).child("height")
                            .setValue(heightTxt)
                    }
                    if (!passwordTxt.isEmpty()) {
                        databaseReference.child("users").child(username).child("password")
                            .setValue(passwordTxt)
                    }
                    if (!conPasswordTxt.isEmpty()){
                        databaseReference.child("users").child(username).child("password").setValue(conPasswordTxt)
                    }

                    //go to profile page
                    val intent = Intent(getContext, ProfileActivity::class.java)
                    startActivity(intent)
                }
                override fun onCancelled(error: DatabaseError) {
                    println("The write failed: " + error.code);
                }
                })
            }
        }

        discardButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }
}