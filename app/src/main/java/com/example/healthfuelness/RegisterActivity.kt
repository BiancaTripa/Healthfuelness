package com.example.healthfuelness

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){

   // private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

     //   auth = FirebaseAuth.getInstance()
/*
        btn_register2.setOnClickListener{
            registerUser()
        }*/

        val loginTextView: TextView = findViewById<TextView>(R.id.tv_login2)
        loginTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // finish()
        }
    }

    private fun registerUser() {

        if(tv_full_name.text.toString().isEmpty()) {
            tv_full_name.error = "Please enter your full name"
            tv_full_name.requestFocus()
            return
        }

        if(tv_email.text.toString().isEmpty()) {
            tv_email.error = "Please enter your email"
            tv_email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_email.text.toString()).matches()){
            tv_email.error = "Please enter valid email"
            tv_email.requestFocus()
            return
        }

        if(tv_password.text.toString().isEmpty()) {
            tv_password.error = "Please enter your password"
            tv_password.requestFocus()
            return
        }

        if(tv_confirm_password.text.toString().isEmpty()) {
            tv_confirm_password.error = "Please confirm your password"
            tv_confirm_password.requestFocus()
            return
        }

        if(tv_confirm_password.text.toString() != tv_password.text.toString()) {
            tv_confirm_password.error = "Passwords do not match"
            tv_confirm_password.requestFocus()
            return
        }

       /* auth.createUserWithEmailAndPassword(tv_email.text.toString(), tv_password.text.toString() )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Register failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }*/

    }
}