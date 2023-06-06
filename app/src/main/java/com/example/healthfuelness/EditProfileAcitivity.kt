package com.example.healthfuelness

import User.getUsername
import User.setUsername
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
    private var currentHeight = 0
    private var currentAge: Int = 18
    private var currentWeight = 35

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
                if (snapshot.hasChild("age")) { //age exists in database
                    currentAge = snapshot.child("age").getValue(Int::class.java)!!
                    age.hint = currentAge.toString()
                } else {
                    //age doesn`t exist in database
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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

            //check if user fill the fields before sending data to firebase
            if (fullNameTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty() || ageTxt.isEmpty() || heightTxt.isEmpty() || weightTxt.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }

            //check if passwords match
            else if (passwordTxt != conPasswordTxt) {
                Toast.makeText(this, "Passwords are not matching", Toast.LENGTH_SHORT).show()
            }

            //send data to firebase RealTime Database
            else {
                val getContext = this
                databaseReference.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        databaseReference.child("users").child(username).child("age").setValue(ageTxt)
                        databaseReference.child("users").child(username).child("weight").setValue(weightTxt)
                        databaseReference.child("users").child(username).child("height").setValue(heightTxt)

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