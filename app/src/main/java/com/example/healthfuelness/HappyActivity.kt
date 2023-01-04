package com.example.healthfuelness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.healthfuelness.databinding.ActivityHappyBinding

class HappyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHappyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHappyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data from previous activity
        val description = intent.getStringExtra("description")
        val date = intent.getStringExtra("date")
        val imageId = intent.getIntExtra("imageId", R.drawable.ic_logo)

        binding.tvDescription.text = description
        binding.tvDate.text = date
        binding.ivHappyPic.setImageResource(imageId)

    }
}