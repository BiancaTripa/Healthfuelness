package com.example.healthfuelness

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


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

        //go to logout
        val logoutNowButton = findViewById<TextView>(R.id.button_logout)
        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
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


        /*
        databaseReference.child("users").child(getUsername()).child("gallery").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                val description = snapshot.child("description").getValue(String::class.java)
                val date = snapshot.child("date").getValue(String::class.java)

                // after getting the value we are setting
                // our value to our text view in below line.
                arrayList.add(GalleryItem(R.drawable.ic_camera, description, date))
            }

            override fun onCancelled(error: DatabaseError) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(this@GalleryActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })

         */


        arrayList.add(GalleryItem(R.drawable.ic_camera, "Camera", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_logo, "Logo", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_map, "Map", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_user, "com.example.healthfuelness.User", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_message, "Message", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_password, "Password", "date"))


        return arrayList
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var galleryItem: GalleryItem = arrayList!![p2]
        Toast.makeText(applicationContext, galleryItem.name, Toast.LENGTH_LONG).show()
    }
}