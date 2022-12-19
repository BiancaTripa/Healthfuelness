package com.example.healthfuelness

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){

    //create object of DatabaseReference class to access firebase's Realtime Database
    val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fullName = findViewById<EditText>(R.id.fullname)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val conPassword = findViewById<EditText>(R.id.conPassword)
        val registerButton = findViewById<Button>(R.id.button_register)
        val loginNowButton = findViewById<TextView>(R.id.button_login)


        registerButton.setOnClickListener {
            //get data from EditTexts into String variables
            val fullNameTxt = fullName.text.toString()
            val emailTxt = email.text.toString()
            val passwordTxt = password.text.toString()
            val conPasswordTxt = conPassword.text.toString()

            //check if user fill the fields before sending data to firebase
            if (fullNameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }

            //check if passwords match
            else if (passwordTxt != conPasswordTxt) {
                Toast.makeText(this, "Passwords are not matching", Toast.LENGTH_SHORT).show()
            }

            //send data to firebase RealTime Database
            else {
                val getContext = this
                databaseReference.child("users").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //check if email is not registered before
                        if (snapshot.hasChild(emailTxt)){
                            Toast.makeText(getContext, "Email already used", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else {
                            //use email as unique identity of every user
                            // other details comes under email
                            databaseReference.child("users").child(emailTxt).child("fullname").setValue(fullNameTxt)
                            databaseReference.child("users").child(emailTxt).child("password").setValue(passwordTxt)

                            Toast.makeText(getContext, "User registered successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }
        }

        loginNowButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}