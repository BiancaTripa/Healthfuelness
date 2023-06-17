package com.example.healthfuelness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.db.williamchart.view.BarChartView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WeightChartActivity : AppCompatActivity() {
    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    private val username = User.getUsername()
    private val selectedDate = User.getDate()
    private val previousDate1 = User.getDate1()
    private val previousDate2 = User.getDate2()
    private val previousDate3 = User.getDate3()
    private val previousDate4 = User.getDate4()
    private val previousDate5 = User.getDate5()
    private val previousDate6 = User.getDate6()
    private val previous7Days =  arrayOf<String>(selectedDate, previousDate1, previousDate2, previousDate3, previousDate4, previousDate5, previousDate6)
    private var previous7ValuesWeight = Previous7Values(0F,0F,0F,0F,0F,0F,0F)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_chart)

        val barchartWeight = findViewById<BarChartView>(R.id.barChartVer)

        databaseReference.child("users").child(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("measurements")) {

                        if (snapshot.child("measurements").hasChild(selectedDate)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesWeight.setCurrent(snapshot.child("measurements").child(selectedDate)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate1)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesWeight.setPrevious1(snapshot.child("measurements").child(previousDate1)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate2)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesWeight.setPrevious2(snapshot.child("measurements").child(previousDate2)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate3)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesWeight.setPrevious3(snapshot.child("measurements").child(previousDate3)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate4)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesWeight.setPrevious4(snapshot.child("measurements").child(previousDate4)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate5)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesWeight.setPrevious5(snapshot.child("measurements").child(previousDate5)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate6)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesWeight.setPrevious6(snapshot.child("measurements").child(previousDate6)
                                .child("weight").value.toString().toFloat())
                        }

                        barchartWeight.animation.duration = 1000L
                        val barSetWater = listOf(Pair(previous7Days[6], previous7ValuesWeight.getPrevious6()),
                            Pair(previous7Days[5], previous7ValuesWeight.getPrevious5()),
                            Pair(previous7Days[4], previous7ValuesWeight.getPrevious4()),
                            Pair(previous7Days[3], previous7ValuesWeight.getPrevious3()),
                            Pair(previous7Days[2], previous7ValuesWeight.getPrevious2()),
                            Pair(previous7Days[1], previous7ValuesWeight.getPrevious1()),
                            Pair(previous7Days[0], previous7ValuesWeight.getCurrent()))
                        barchartWeight.animate(barSetWater)

                    }
                }

                override fun onCancelled(firebaseError: DatabaseError) {
                    println("The read failed: " + firebaseError.message)
                }
            })

        //go to home
        val GoToHomePageButton = findViewById<ImageView>(R.id.button_home_page_weight)
        GoToHomePageButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //go back
        val GoBackButton = findViewById<ImageView>(R.id.button_back_weight)
        GoBackButton.setOnClickListener {
            val intent = Intent(this, HomeMeasurementsActivity::class.java)
            startActivity(intent)
        }
    }
}
