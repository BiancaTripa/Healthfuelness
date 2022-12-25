package com.example.healthfuelness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val logoutNowButton = findViewById<TextView>(R.id.button_logout)
        val whatMakeMeHappyButton = findViewById<ImageView>(R.id.button_happy)
        val homePageButton = findViewById<ImageView>(R.id.button_home)
        val quoteText = findViewById<TextView>(R.id.tv_daily_quote)

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


        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        whatMakeMeHappyButton.setOnClickListener {
            val intent = Intent(this, WhatMakeMeHappyActivity::class.java)
            startActivity(intent)
        }

        homePageButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}