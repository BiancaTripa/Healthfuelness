package com.example.healthfuelness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.ktx.storage
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageActivity : AppCompatActivity() {

    private lateinit var photoImageView: ImageView
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        photoImageView = findViewById(R.id.grid_view)
        //storage = Firebase.storage

        val imageUrl = "YOUR_IMAGE_URL_FROM_FIREBASE"  //replace with actual url from firebase
        downloadImage(imageUrl)
    }

    private fun downloadImage(imageUrl: String) {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(input)
        displayImage(bitmap)
    }

    private fun displayImage(bitmap: Bitmap) {
        photoImageView.setImageBitmap(bitmap)
    }

}