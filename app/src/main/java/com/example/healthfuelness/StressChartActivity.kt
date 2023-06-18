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

class StressChartActivity : AppCompatActivity() {

    private val username = User.getUsername()
    private val selectedDate = User.getDate()
    private val selectedDateJustMonthAndDay = selectedDate.substring(5)
    private val selectedDateJustYearAndMonth = selectedDate.substring(0, 6)
    private val selectedDateJustDay = selectedDate.substring(7)

    //create object of DatabaseReference class to access firebase's Realtime Database
    private val databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthfuelness-d8e8a-default-rtdb.firebaseio.com/")

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
    private val previous7Days =  arrayOf<String>(selectedDateJustMonthAndDay, previousDate1JustMonthAndDay, previousDate2JustMonthAndDay, previousDate3JustMonthAndDay, previousDate4JustMonthAndDay, previousDate5JustMonthAndDay, previousDate6JustMonthAndDay)
    private var previous7ValuesStress = Previous7Values(0F,0F,0F,0F,0F,0F,0F)

    private var stressValuesObject = StressValues(100, 0, 100, 0, emptyArray(), emptyArray())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stress_chart)

        val tvDate = findViewById<TextView>(R.id.tv_selectedDate)
        val barchartStress = findViewById<BarChartView>(R.id.barChartVerStress)
        val GoToHomePageButton = findViewById<ImageView>(R.id.button_home_page_water)
        val GoBackButton = findViewById<TextView>(R.id.button_back_water)

        val ivStressMin = findViewById<ImageView>(R.id.button_stress_min)
        val ivStressMax = findViewById<ImageView>(R.id.button_stress_max)

        val tvWaterMin = findViewById<TextView>(R.id.tv_water_min)
        val tvWaterMax = findViewById<TextView>(R.id.tv_water_max)

        //Selected date
        tvDate.text = selectedDate

        //go to home
        GoToHomePageButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //go back
        GoBackButton.setOnClickListener {
            val intent = Intent(this, HomeMeasurementsActivity::class.java)
            startActivity(intent)
        }

        databaseReference.child("users").child(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.hasChild("measurements")) {

                        if (snapshot.child("measurements").hasChild(selectedDate)) { // data exist for this user, in this date
                            previous7ValuesStress.setCurrent(snapshot.child("measurements").child(selectedDate)
                                .child("stressLevel").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate1)) { // data exist for this user, in this date
                            previous7ValuesStress.setPrevious1(snapshot.child("measurements").child(previousDate1)
                                .child("stressLevel").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate2)) { // data exist for this user, in this date
                            previous7ValuesStress.setPrevious2(snapshot.child("measurements").child(previousDate2)
                                .child("stressLevel").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate3)) { // data exist for this user, in this date
                            previous7ValuesStress.setPrevious3(snapshot.child("measurements").child(previousDate3)
                                .child("stressLevel").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate4)) { // data exist for this user, in this date
                            previous7ValuesStress.setPrevious4(snapshot.child("measurements").child(previousDate4)
                                .child("stressLevel").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate5)) { // data exist for this user, in this date
                            previous7ValuesStress.setPrevious5(snapshot.child("measurements").child(previousDate5)
                                .child("stressLevel").value.toString().toFloat())
                        }

                        if (snapshot.child("measurements").hasChild(previousDate6)) { // data exist for this user, in this date
                            previous7ValuesStress.setPrevious6(snapshot.child("measurements").child(previousDate6)
                                .child("stressLevel").value.toString().toFloat())
                        }

                        // iterate the database in current month from current day to beginning
                      /*  for (aux in selectedDateJustDay.toInt() downTo 1 step 1) {
                            if (snapshot.child("measurements").child(selectedDateJustYearAndMonth).hasChild(aux.toString())) {
                                //data in aux day exist
                                val stressAux = snapshot.child("measurements").child(selectedDateJustYearAndMonth).child(aux.toString())
                                    .child("stressLevel").getValue(Int::class.java)!!

                                if (stressValuesObject.getStressMin() > stressAux) {
                                    //daca valoarea gasita este mai mica decat minimul avut
                                    //isi dau reset array-urile si val min a stresului
                                    stressValuesObject.setStressMin(stressAux)
                                    //reset array val min apa
                                    stressValuesObject.createArrayMinWithFirstValue(snapshot.child("measurements").child(selectedDateJustYearAndMonth).child(aux.toString())
                                        .child("water").getValue(Int::class.java)!!)
                                } else if (stressValuesObject.getStressMin() == stressAux) {
                                    //daca e acc val ca min, se adauga in array ul de min
                                    //apa
                                    stressValuesObject.addValueInArrayMin(snapshot.child("measurements").child(selectedDateJustYearAndMonth).child(aux.toString())
                                        .child("water").getValue(Int::class.java)!!)
                                }

                                if (stressValuesObject.getStressMax() < stressAux) {
                                    //daca valoarea gasita este mai mare decat maximul avut
                                    //isi dau reset array-urile si val max a stresului
                                    stressValuesObject.setStressMax(stressAux)
                                    //reset array val max apa
                                    stressValuesObject.createArrayMaxWithFirstValue(snapshot.child("measurements").child(selectedDateJustYearAndMonth).child(aux.toString())
                                        .child("water").getValue(Int::class.java)!!)
                                } else if (stressValuesObject.getStressMax() == stressAux) {
                                    //daca e acc val ca max, se adauga in array ul de max
                                    //apa
                                    stressValuesObject.addValueInArrayMax(snapshot.child("measurements").child(selectedDateJustYearAndMonth).child(aux.toString())
                                        .child("water").getValue(Int::class.java)!!)
                                }
                            } else {
                                //data in aux day not exist
                            }
                        }
                        //Stress level Min
                        if (stressValuesObject.getStressMin() == 1) {
                            ivStressMin.setImageResource(R.drawable.stressed_out);
                        }else if(stressValuesObject.getStressMin() == 2){
                            ivStressMin.setImageResource(R.drawable.stressed);
                        }else if(stressValuesObject.getStressMin() == 3){
                            ivStressMin.setImageResource(R.drawable.thinking);
                        }else if(stressValuesObject.getStressMin() == 4){
                            ivStressMin.setImageResource(R.drawable.happy);
                        }else if(stressValuesObject.getStressMin() == 5){
                            ivStressMin.setImageResource(R.drawable.very_happy);
                        }else {
                            //empty
                        }

                        //Stress level Max
                        if (stressValuesObject.getStressMax() == 1) {
                            ivStressMax.setImageResource(R.drawable.stressed_out);
                        }else if(stressValuesObject.getStressMax() == 2){
                            ivStressMax.setImageResource(R.drawable.stressed);
                        }else if(stressValuesObject.getStressMax() == 3){
                            ivStressMax.setImageResource(R.drawable.thinking);
                        }else if(stressValuesObject.getStressMax() == 4){
                            ivStressMax.setImageResource(R.drawable.happy);
                        }else if(stressValuesObject.getStressMax() == 5){
                            ivStressMax.setImageResource(R.drawable.very_happy);
                        }else {
                            //empty
                        }

                        //water
                        tvWaterMin.text = stressValuesObject.makeMediaOfArrayMin().toString()
                        tvWaterMax.text = stressValuesObject.makeMediaOfArrayMax().toString()*/

                    } else { //measurements not exist for this user
                        //water
                        tvWaterMin.text = "empty"
                        tvWaterMax.text = "empty"
                    }


                    barchartStress.animation.duration = 1000L
                    val barSetStress = listOf(Pair(previous7Days[6], previous7ValuesStress.getPrevious6()),
                        Pair(previous7Days[5], previous7ValuesStress.getPrevious5()),
                        Pair(previous7Days[4], previous7ValuesStress.getPrevious4()),
                        Pair(previous7Days[3], previous7ValuesStress.getPrevious3()),
                        Pair(previous7Days[2], previous7ValuesStress.getPrevious2()),
                        Pair(previous7Days[1], previous7ValuesStress.getPrevious1()),
                        Pair(previous7Days[0], previous7ValuesStress.getCurrent()))
                    barchartStress.animate(barSetStress)
                }

                override fun onCancelled(firebaseError: DatabaseError) {
                    println("The read failed: " + firebaseError.message)
                }
            }
         )

    }
}