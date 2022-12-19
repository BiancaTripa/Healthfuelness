package com.example.healthfuelness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.button_login)
        val registerNowButton = findViewById<TextView>(R.id.button_register)

        loginButton.setOnClickListener {
            val emailTxt = email.text.toString()
            val passwordTxt = password.text.toString()

            if (emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                Toast.makeText(this, "Please enter your email or password", Toast.LENGTH_SHORT).show()
            }
            else {
                val getContext = this
                databaseReference.child("users").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //check if email is exist in firebase database

                        if (snapshot.hasChild(emailTxt)) {
                            val getPassword = snapshot.child(emailTxt).child("password").getValue(String.javaClass)

                            if (getPassword != null) {
                                if (getPassword.equals(passwordTxt)) {
                                    Toast.makeText(getContext, "Successfully logged in", Toast.LENGTH_SHORT).show()
                                    //open another activity
                                }
                                else {
                                    Toast.makeText(getContext, "Wrong password", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }else {
                            Toast.makeText(getContext, "Wrong email", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

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