package com.example.healthfuelness

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.db.williamchart.view.BarChartView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlin.math.roundToInt

class MoreDetailsActivity: AppCompatActivity() {

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    private val username = User.getUsername()
    private val selectedDate = User.getDate()
    private val previousDate1 = User.getDate1()
    private val previousDate2 = User.getDate2()
    private val previousDate3 = User.getDate3()
    private val previousDate4 = User.getDate4()

    private val previous5Days =  arrayOf<String>(selectedDate, previousDate1, previousDate2, previousDate3, previousDate4)
    private val previous5ValuesToluene =  arrayOf(0.0F, 0.0F, 0.0F, 0.0F, 0.0F)

    private val textPopupToluene = "\t" + "\t" +  "Este important să se evite expunerea prelungită și repetată la toluen," +
            " deoarece poate provoca efecte cumulative și daune grave sănătății. " + "\n" +
            "\t" +  "\t" + "Poate provoca iritații respiratorii, neurologice și cutanate, precum și efecte asupra ochilor și sistemului cardiovascular." + "\n" +
            "\t" +  "\t" + "În cazul în care suspectați o expunere la toluen și dezvoltați simptome neobișnuite,"  +
            " este recomandat să căutați asistență medicală și să discutați cu un profesionist de sănătate calificat pentru evaluare și tratament adecvat."


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_details)

        //declaration for buttons and text views from xml
        val buttonBack = findViewById<TextView>(R.id.button_back)
        val buttonGoHome = findViewById<ImageView>(R.id.button_home_page)
        val textViewSelectedDate = findViewById<TextView>(R.id.tv_selectedDate)
        val barChartForToluene = findViewById<BarChartView>(R.id.barChartToluene)
        val openPupUpToluene = findViewById<TextView>(R.id.tv_popup_toluene)
        val textViewTolueneMin = findViewById<TextView>(R.id.tv_toluene_min)
        val textViewTolueneMax = findViewById<TextView>(R.id.tv_toluene_max)

        //check if data from arduino in the current date exists in firebase realtime database
        // if data exists, show minim and maxim values and add the max val in list for bar chart

        databaseReference.child("users").child(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("dataFromArduino")) {
                        if (snapshot.child("dataFromArduino").hasChild(selectedDate)) { // data exist for this user, in this date

                            //Toluene
                            textViewTolueneMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("tolueneMin").value.toString()
                            val maxToluene = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("tolueneMax").value
                            textViewTolueneMax.text = maxToluene.toString()
                            val myFloat: Float = maxToluene.toString().toFloat()
                            previous5ValuesToluene[0] = myFloat

                        } else { // dataFromArduino in selected date not exits for this user

                        }
                    } else { // dataFromArduino not exits for this user

                    }
                }

                override fun onCancelled(firebaseError: DatabaseError) {
                    println("The read failed: " + firebaseError.message)
                }
            })




        //check if data from arduino in the previous date exists in firebase realtime database
        // if data exists, add the max val in list for bar chart
        // if data exists, add the value 0 in list for bar chart
        databaseReference.child("users").child(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("dataFromArduino")) {
                        if (snapshot.child("dataFromArduino").hasChild(previousDate1)) { // data exist for this user, in this date
                            //Toluene
                            previous5ValuesToluene[3] = snapshot.child("dataFromArduino").child(previousDate1)
                                .child("tolueneMax").value.toString().toFloat()
                        } else { // dataFromArduino in this date not exits for this user
                            //Toluene
                            previous5ValuesToluene[3] = 0.0F
                        }

                        if (snapshot.child("dataFromArduino").hasChild(previousDate2)) { // data exist for this user, in this date
                            //Toluene
                            previous5ValuesToluene[2] = snapshot.child("dataFromArduino").child(previousDate2)
                                .child("tolueneMax").value.toString().toFloat()
                        } else { // dataFromArduino in this date not exits for this user
                            //Toluene
                            previous5ValuesToluene[2] = 0.0F
                        }

                        if (snapshot.child("dataFromArduino").hasChild(previousDate3)) { // data exist for this user, in this date
                            //Toluene
                            previous5ValuesToluene[1] = snapshot.child("dataFromArduino").child(previousDate3)
                                .child("tolueneMax").value.toString().toFloat()
                        } else { // dataFromArduino in this date not exits for this user
                            //Toluene
                            previous5ValuesToluene[1] = 0.0F
                        }

                        if (snapshot.child("dataFromArduino").hasChild(previousDate4)) { // data exist for this user, in this date
                            //Toluene
                            previous5ValuesToluene[0] = snapshot.child("dataFromArduino").child(previousDate4)
                                .child("tolueneMax").value.toString().toFloat()
                        } else { // dataFromArduino in this date not exits for this user
                            //Toluene
                            previous5ValuesToluene[0] = 0.0F
                        }

                    } else { // dataFromArduino not exits for this user
                        //Toluene
                        previous5ValuesToluene[3] = 0.0F
                        previous5ValuesToluene[2] = 0.0F
                        previous5ValuesToluene[1] = 0.0F
                        previous5ValuesToluene[0] = 0.0F
                    }
                }

                override fun onCancelled(firebaseError: DatabaseError) {
                    println("The read failed: " + firebaseError.message)
                }
            })

        //Selected date
        textViewSelectedDate.text = selectedDate

        //back button
        buttonBack.setOnClickListener {
            val intent = Intent(this, ArduinoPreviousActivity::class.java)
            startActivity(intent)
        }

        //home button
        buttonGoHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //popup toluene button
        openPupUpToluene.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.layout_popup, null)
            window.contentView = view
            val tvInfo = view.findViewById<TextView>(R.id.tv_info)

            tvInfo.text = textPopupToluene
            tvInfo.setOnClickListener {
                window.dismiss()
            }
            window.showAsDropDown(openPupUpToluene)

        }

        //Bar Chart for Toluene
        barChartForToluene.animation.duration = 1000L
        val barSetToluene = listOf(Pair(previous5Days[4], previous5ValuesToluene[4]),
                                    Pair(previous5Days[3], previous5ValuesToluene[3]),
                                    Pair(previous5Days[2], previous5ValuesToluene[2]),
                                    Pair(previous5Days[1], previous5ValuesToluene[1]),
                                    Pair(previous5Days[0], previous5ValuesToluene[0]))
        barChartForToluene.animate(barSetToluene)

    }
}