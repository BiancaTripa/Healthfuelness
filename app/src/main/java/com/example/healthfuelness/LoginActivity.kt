package com.example.healthfuelness

import com.example.healthfuelness.User.setUsername
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fullName = findViewById<EditText>(R.id.fullName)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.button_login)
        val registerNowButton = findViewById<TextView>(R.id.button_register)

        loginButton.setOnClickListener {
            val fullNameTxt = fullName.text.toString()
            val passwordTxt = password.text.toString()

            if (fullNameTxt.isEmpty() || passwordTxt.isEmpty()) {
                Toast.makeText(this, "Please enter your username or password", Toast.LENGTH_SHORT).show()
            }
            else {
                val getContext = this
                databaseReference.child("users").addListenerForSingleValueEvent(object :
                    ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        //check if email is exist in firebase database
                        if (snapshot.hasChild(fullNameTxt)) {

                            val getPassword = snapshot.child(fullNameTxt).child("password").getValue(String::class.java)
                            if (getPassword != null) {

                                if (getPassword.equals(passwordTxt)) {
                                    setUsername(fullNameTxt)
                                    Toast.makeText(getContext, "Successfully logged in", Toast.LENGTH_SHORT).show()
                                    //go to home page
                                    val intent = Intent(getContext, HomeActivity::class.java)
                                    startActivity(intent)
                                }
                                else {
                                    Toast.makeText(getContext, "Wrong password", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(getContext, "Wrong username", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("The read failed: " + error.code);
                    }
                })
            }
        }

        registerNowButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}