package com.example.healthfuelness

import User.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.healthfuelness.databinding.ActivityHomeBinding
import com.example.healthfuelness.databinding.ActivityWhatMakeMeHappyBinding

class WhatMakeMeHappyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWhatMakeMeHappyBinding
    private lateinit var happyPhotoArrayList: ArrayList<HappyPhoto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         val logoutNowButton= findViewById<TextView>(R.id.button_logout)
         val homePageButton = findViewById<ImageView>(R.id.button_home)

        setContentView(R.layout.activity_what_make_me_happy)
        binding = ActivityWhatMakeMeHappyBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        val imageId = intArrayOf(

            R.drawable.ic_camera, R.drawable.ic_map, R.drawable.ic_logo, R.drawable.ic_user
        )

        val description = arrayOf(

            "camera",
            "map",
            "logo",
            "user"
        )

        val date = arrayOf(

            "1",
            "2",
            "3",
            "4"
        )

        happyPhotoArrayList = ArrayList()

        for (i in description.indices) {
            val happyPhoto = HappyPhoto(description[i], date[i], imageId[i])
            happyPhotoArrayList.add(happyPhoto)
        }

        binding.listview.isClickable = true
        binding.listview.adapter = MyAdapter(this, happyPhotoArrayList)
        binding.listview.setOnItemClickListener { parent, view, position, id ->

            val selectedDescription = description[position]
            val selectedDate = date[position]
            val selectedImageId  = imageId[position]

            val i = Intent(this, HappyActivity::class.java)
            //to send the data to the next activity
            // i.putExtra("description", description)
            setSelectedDescription(selectedDescription)
            // i.putExtra("date", date)
            setSelectedDate(selectedDate)
            // i.putExtra("imageId", imageId)
            setSelectedImageId(selectedImageId)
            startActivity(i)
        }

        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        homePageButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }



    }
}