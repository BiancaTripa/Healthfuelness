package com.example.healthfuelness

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.healthfuelness.User.getUsername
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


abstract class ProfileActivity : AppCompatActivity(), OnMapReadyCallback {



    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")
    private var currentMeasure = 0

   // private void deleteMeasurement(String measurementID){
        //DatabaseReference measure = FirebaseDatabase.getInstance().getRefrence("measurements")
    //}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logoutNowButton = findViewById<TextView>(R.id.button_logout)
        val buttonEditProfile = findViewById<LinearLayout>(R.id.edit_profile)
        val buttonNotificationSettings = findViewById<LinearLayout>(R.id.notifications_settings)
        val buttonPrivacyManagement = findViewById<LinearLayout>(R.id.privacy_management)
        val buttonDeleteAccount = findViewById<LinearLayout>(R.id.delete_account)

        //go to home
        val homeButton = findViewById<ImageView>(R.id.button_homeprofile)
        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //go to logout
        logoutNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //go to edit profile
        buttonEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        //go to edit notifications
        buttonNotificationSettings.setOnClickListener {
            val intent = Intent(this, NotificationSettingsActivity::class.java)
            startActivity(intent)
        }

        val username = getUsername()



        //clears all user data - measurements
        databaseReference.child("users").child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //check if measurements exists in firebase database
                if (snapshot.hasChild("measurements")) { //node measurements exists in database
                    currentMeasure = snapshot.child("measurements").getValue(Int::class.java)!!
                    //snapshot.child("measurements").removeValue();
                } else {
                    //measurements doesn`t exist in database
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        buttonPrivacyManagement.setOnClickListener {
            //val intent = Intent(this, NotificationSettingsActivity::class.java)
            //startActivity(intent)
        }

        //delete user - remove username node
        //buttonDeleteAccount.setOnClickListener {
           // AlertDialog.Builder() builder = new AlertDialog.Builder(buttonDeleteAccount.getContext(ProfileActivity.this));
            //builder.setTitle("Are you sure?")
            //builder.setMessage("Deleted data can't be recovered")
       // }


    }


}