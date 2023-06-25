package com.example.healthfuelness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.db.williamchart.view.BarChartView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SleepChartActivity : AppCompatActivity() {

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

    private val username = User.getUsername()
    private val selectedDate = User.getDate()
    private val previousDate1 = User.getDate1()
    private val previousDate1JustMonthAndDay = previousDate1.substring(5)
    private val previousDate2 = User.getDate2()
    private val previousDate2JustMonthAndDay = previousDate2.substring(5)
    private val previousDate3 = User.getDate3()
    private val previousDate3JustMonthAndDay = previousDate3.substring(5)
    private val previousDate4 = User.getDate4()
    private val previousDate4JustMonthAndDay = previousDate4.substring(5)
    private val previousDate5 = User.getDate5()
    private val previousDate5JustMonthAndDay = previousDate5.substring(5)
    private val previousDate6 = User.getDate6()
    private val previousDate6JustMonthAndDay = previousDate6.substring(5)
    private val previous7Days =  arrayOf<String>(selectedDate, previousDate1JustMonthAndDay, previousDate2JustMonthAndDay, previousDate3JustMonthAndDay, previousDate4JustMonthAndDay, previousDate5JustMonthAndDay, previousDate6JustMonthAndDay)
    private var previous7ValuesSleep = Previous7Values(0F,0F,0F,0F,0F,0F,0F)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_chart)

        val barchartWeight = findViewById<BarChartView>(R.id.barChartSleep)
        val tvDate = findViewById<TextView>(R.id.tv_selectedDate)
        //Selected date
        tvDate.text = selectedDate
/*
        databaseReference.child("users").child(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("measurements")) {

                        if (snapshot.child("measurements").hasChild(selectedDate)) { // data exist for this user, in this date
                            var counter = 24 - snapshot.child("measurements").child(selectedDate).child("hourSleep") +
                                    snapshot.child("measurements").child(selectedDate).child("hourWakeup")

                            previous7ValuesSleep.setCurrent(snapshot.child("measurements").child(selectedDate)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate1)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesSleep.setPrevious1(snapshot.child("measurements").child(previousDate1)
                                .(24 - child("hourSleep") + child("hourWakeup")).value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate2)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesSleep.setPrevious2(snapshot.child("measurements").child(previousDate2)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate3)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesSleep.setPrevious3(snapshot.child("measurements").child(previousDate3)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate4)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesSleep.setPrevious4(snapshot.child("measurements").child(previousDate4)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate5)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesSleep.setPrevious5(snapshot.child("measurements").child(previousDate5)
                                .child("weight").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate6)) { // data exist for this user, in this date
                            // Weight
                            previous7ValuesSleep.setPrevious6(snapshot.child("measurements").child(previousDate6)
                                .child("weight").value.toString().toFloat())
                        }

                        barchartWeight.animation.duration = 1000L
                        val barSetWater = listOf(Pair(previous7Days[6], previous7ValuesSleep.getPrevious6()),
                            Pair(previous7Days[5], previous7ValuesSleep.getPrevious5()),
                            Pair(previous7Days[4], previous7ValuesSleep.getPrevious4()),
                            Pair(previous7Days[3], previous7ValuesSleep.getPrevious3()),
                            Pair(previous7Days[2], previous7ValuesSleep.getPrevious2()),
                            Pair(previous7Days[1], previous7ValuesSleep.getPrevious1()),
                            Pair(previous7Days[0], previous7ValuesSleep.getCurrent()))
                        barchartWeight.animate(barSetWater)

                    }
                }

                override fun onCancelled(firebaseError: DatabaseError) {
                    println("The read failed: " + firebaseError.message)
                }
        })*/

        //go to home
        val GoToHomePageButton = findViewById<ImageView>(R.id.button_home_page_sleep)
        GoToHomePageButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //go back
        val GoBackButton = findViewById<TextView>(R.id.button_back_sleep)
        GoBackButton.setOnClickListener {
            val intent = Intent(this, HomeMeasurementsActivity::class.java)
            startActivity(intent)
        }
    }

}
