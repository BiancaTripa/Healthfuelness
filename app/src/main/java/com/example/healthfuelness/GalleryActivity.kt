package com.example.healthfuelness

import com.example.healthfuelness.User.getUsername
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class GalleryActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")


    private var gridView: GridView ?= null
    private var arrayList: ArrayList<GalleryItem> ?= null
    private var galleryAdapter: GalleryAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        gridView = findViewById(R.id.grid_view)
        arrayList = ArrayList()
        arrayList = setDataList()
        galleryAdapter = GalleryAdapter(applicationContext, arrayList!!)
        gridView?.adapter = galleryAdapter
        gridView?.onItemClickListener = this

        //add new photo
        val addButton = findViewById<ImageView>(R.id.button_add)
        addButton.setOnClickListener {
            val intent = Intent(this, HappyActivity::class.java)
            startActivity(intent)
        }

        //go back
        val backNowButton = findViewById<TextView>(R.id.btn_back_gallery)
        backNowButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //go to home
        val homeButton = findViewById<ImageView>(R.id.button_homemeasurements)
        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setDataList(): ArrayList<GalleryItem> {
        var arrayList: ArrayList<GalleryItem> = ArrayList()
        databaseReference.child("users").child(getUsername()).child("gallery").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val imageId = childSnapshot.key
                    // Retrieve the fields of each image (date, description, etc.)
                    val date = childSnapshot.child("date").getValue(String::class.java)
                    val description = childSnapshot.child("description").getValue(String::class.java)
                    // Add the image data to the adapter
                    val imageBitmap = convertImagepathToBitmap(childSnapshot.child("imagePath"))
                    arrayList.add(GalleryItem(imageBitmap, description, date))
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@GalleryActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
        return arrayList
    }

    fun convertChildrenToBitmap(dataSnapshot: DataSnapshot): Bitmap? {
        val byteArray = dataSnapshot.getValue<ByteArray>()
        return byteArray?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }

    fun convertImagepathToBitmap(imagepathSnapshot: DataSnapshot): Bitmap? {
        val byteArray = mutableListOf<Byte>()
        for (childSnapshot in imagepathSnapshot.children) {
            val byteValue = childSnapshot.getValue<Int>()
            byteValue?.let { byteArray.add(it.toByte()) }
        }
        val byteArrayPrimitive = byteArray.toByteArray()
        return BitmapFactory.decodeByteArray(byteArrayPrimitive, 0, byteArrayPrimitive.size)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var galleryItem: GalleryItem = arrayList!![p2]
        Toast.makeText(applicationContext, galleryItem.name, Toast.LENGTH_LONG).show()
    }
}