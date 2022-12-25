package com.example.healthfuelness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val logoutNowButton = findViewById<TextView>(R.id.button_logout)
        val whatMakeMeHappyButton = findViewById<ImageView>(R.id.button_happy)
        val homePageButton = findViewById<ImageView>(R.id.button_home)


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