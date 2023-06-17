package com.example.healthfuelness

import com.example.healthfuelness.User
import com.example.healthfuelness.User.setUsername
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity(){
    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fullName = findViewById<EditText>(R.id.fullname)
        val age = findViewById<EditText>(R.id.age)
        val height = findViewById<EditText>(R.id.tv_height)
        val weight = findViewById<EditText>(R.id.tv_weight)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val conPassword = findViewById<EditText>(R.id.conPassword)
        val registerButton = findViewById<Button>(R.id.button_register)
        val loginNowButton = findViewById<TextView>(R.id.button_login)

        registerButton.setOnClickListener {
            //get data from EditTexts into String variables
            val fullNameTxt = fullName.text.toString()
            val emailTxt = email.text.toString()
            val ageTxt = age.text.toString()
            val heightTxt = height.text.toString()
            val weightTxt = weight.text.toString()
            val passwordTxt = password.text.toString()
            val conPasswordTxt = conPassword.text.toString()

            //check if user fill the fields before sending data to firebase
            if (fullNameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty() || ageTxt.isEmpty() || heightTxt.isEmpty() || weightTxt.isEmpty()) {
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

                        //check if username is not registered before
                        if (snapshot.hasChild(fullNameTxt)){
                            Toast.makeText(getContext, "Username already used", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            //use username(full name) as unique identity of every user
                            // other details comes under username(full name)
                            val window = PopupWindow(getContext)
                            val view = layoutInflater.inflate(R.layout.layout_popup_accept_or_deny, null)
                            val tvInfo = view.findViewById<TextView>(R.id.tv_info)
                            val btnAccept = view.findViewById<Button>(R.id.btn_accept)
                            val btnDeny = view.findViewById<Button>(R.id.btn_deny)

                            window.contentView = view
                            tvInfo.text = "Allow Healthfuelness to process your personal data?"

                            btnAccept.setOnClickListener{
                                databaseReference.child("users").child(fullNameTxt).child("email").setValue(emailTxt)
                                databaseReference.child("users").child(fullNameTxt).child("age").setValue(ageTxt)
                                databaseReference.child("users").child(fullNameTxt).child("height").setValue(heightTxt)
                                databaseReference.child("users").child(fullNameTxt).child("weight").setValue(weightTxt)
                                databaseReference.child("users").child(fullNameTxt).child("fullname").setValue(fullNameTxt)
                                databaseReference.child("users").child(fullNameTxt).child("password").setValue(passwordTxt)

                                setUsername(fullNameTxt)

                                Toast.makeText(getContext, "User registered successfully", Toast.LENGTH_SHORT).show()
                                window.dismiss()

                                //go to home page
                                val intent = Intent(getContext, HomeActivity::class.java)
                                startActivity(intent)
                            }
                            btnDeny.setOnClickListener {
                                Toast.makeText(getContext, "Cannot register without agreement", Toast.LENGTH_SHORT).show()
                                window.dismiss()
                            }
                            window.showAsDropDown(age)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        println("The write failed: " + error.code);
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