package com.example.healthfuelness

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
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
import kotlinx.android.synthetic.main.activity_home_measurements.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.PopupWindow

class ProfileActivity : AppCompatActivity(){
    //val height = findViewById<EditText>(R.id.tv_height)
    //val weight = findViewById<TextView>(R.id.tv_weight)
    val username = getUsername()

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val backButton = findViewById<TextView>(R.id.button_back_profile)
        val logoutNowButton = findViewById<LinearLayout>(R.id.log_out_profile)
        val buttonEditProfile = findViewById<LinearLayout>(R.id.edit_profile)
        val buttonNotificationSettings = findViewById<LinearLayout>(R.id.notifications_settings)
        val buttonPrivacyManagement = findViewById<LinearLayout>(R.id.privacy_management)
        val buttonDeleteAccount = findViewById<LinearLayout>(R.id.delete_account)
        val context = this

        //go to home
        val homeButton = findViewById<ImageView>(R.id.button_home_profile)
        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //go back
        backButton.setOnClickListener {
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



        //clears all user data - measurements
        buttonPrivacyManagement.setOnClickListener {
            databaseReference.child("users").child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //check if measurements exists in firebase database and set them to default values
                    if (snapshot.hasChild("measurements")) { //node measurements exists in database
                        val window = PopupWindow(context)
                        val view = layoutInflater.inflate(R.layout.layout_popup_accept_or_deny, null)
                        val tvInfo = view.findViewById<TextView>(R.id.tv_info)
                        val btnAccept = view.findViewById<Button>(R.id.btn_accept)
                        val btnDeny = view.findViewById<Button>(R.id.btn_deny)

                        window.contentView = view
                        tvInfo.text = "Are you sure you want to delete the measurements history? Data can not be recovered."

                        btnAccept.setOnClickListener{
                            databaseReference.child("users").child(username).child("measurements").setValue(null)
                            Toast.makeText(context, "User history for measurements deleted successfully", Toast.LENGTH_SHORT).show()
                            window.dismiss()
                        }
                        btnDeny.setOnClickListener {
                            window.dismiss()
                        }
                        window.showAsDropDown(buttonPrivacyManagement)
                    } else {
                        //measurements doesn`t exist in database, no need of deleting
                        Toast.makeText(
                            context,
                            "You don't have anything to delete",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    println("The read failed: " + error.message)
                }
            })
        }

        //delete user - remove username node
        buttonDeleteAccount.setOnClickListener {
            databaseReference.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //check if measurements exists in firebase database and set them to default values
                    if (snapshot.hasChild(username)) { //node username exists in database
                        val window = PopupWindow(context)
                        val view = layoutInflater.inflate(R.layout.layout_popup_accept_or_deny, null)
                        val tvInfo = view.findViewById<TextView>(R.id.tv_info)
                        val btnAccept = view.findViewById<Button>(R.id.btn_accept)
                        val btnDeny = view.findViewById<Button>(R.id.btn_deny)

                        window.contentView = view
                        tvInfo.text = "Are you sure you want to delete the account? You won't have access to the app unless you will create another account"

                        btnAccept.setOnClickListener{
                            databaseReference.child("users").child(username).setValue(null)
                            val intent = Intent(context, RegisterActivity::class.java)
                            startActivity(intent)
                        }

                        btnDeny.setOnClickListener {
                            window.dismiss()
                        }
                        window.showAsDropDown(buttonPrivacyManagement)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    println("The read failed: " + error.message)
                }
            })
        }
    }
}