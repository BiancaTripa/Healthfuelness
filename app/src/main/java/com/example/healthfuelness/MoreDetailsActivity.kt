package com.example.healthfuelness

import android.annotation.SuppressLint
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

    private val textPopupToluene = "\t" + "\t" +  "Toluene can adversely affect human health by causing respiratory irritation, central nervous system disorders, headaches, dizziness, and long-term exposure may lead to more severe effects on organs such as the liver and kidneys."
    private val textPopupAcetone = "\t" + "\t" + "Acetone can impact human health by causing irritation to the eyes, skin, and respiratory system. " +
            "\n" + "\t" + "\t" +  "Inhalation or ingestion of acetone can lead to headaches, dizziness, nausea, and in high concentrations, it may cause central nervous system depression."
    private val textPopupAmmonia = "\t" + "\t" + "Ammonia can cause respiratory irritation, eye irritation, and skin burns. " +
            "\n" + "\t" + "\t" + "High levels of exposure may lead to lung damage, coughing, and difficulty breathing. " +
            "\n" + "\t" + "\t" + "Seek medical attention if exposed to ammonia and experiencing symptoms."
    private val textPopupAlcohol = "\t" + "\t" + "Inhaling alcohol vapor can irritate the respiratory system, leading to coughing, sore throat, and difficulty breathing. " +
            "\n" + "\t" + "\t" + "It can also cause headaches, dizziness, and nausea. " +
            "\n" + "+\t" + "\t" + "Prolonged exposure may result in organ damage. " +
            "\n" + "\t" + "\t" + "Seek medical help if necessary."
    private val textPopupHydrogen = "\t" + "\t" + "Hydrogen gas is generally not harmful when inhaled in small quantities. " +
            "\n" + "\t" + "\t" + "However, high concentrations of hydrogen can displace oxygen, leading to asphyxiation. " +
            "\n" + "\t" + "\t" + "It is important to ensure proper ventilation when working with or around hydrogen gas."
    private val textPopupCO2 = "\t" + "\t" + "Exposure to high levels of carbon dioxide can cause dizziness, headache, confusion, shortness of breath, and even loss of consciousness. " +
            "\n" + "\t" + "\t" + "It is important to ensure proper ventilation and avoid prolonged exposure to elevated carbon dioxide levels."

    private var previous5ValuesToluene = Previous5Values(0F,0F,0F,0F,0F)
    private var previous5ValuesAcetone = Previous5Values(0F,0F,0F,0F,0F)
    private var previous5ValuesAmmonia = Previous5Values(0F,0F,0F,0F,0F)
    private var previous5ValuesAlcohol = Previous5Values(0F,0F,0F,0F,0F)
    private var previous5ValuesHydrogen = Previous5Values(0F,0F,0F,0F,0F)
    private var previous5ValuesCO2 = Previous5Values(0F,0F,0F,0F,0F)

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_details)

        //declaration for buttons and text views from xml
        val buttonBack = findViewById<TextView>(R.id.button_back)
        val buttonGoHome = findViewById<ImageView>(R.id.button_home_page)
        val textViewSelectedDate = findViewById<TextView>(R.id.tv_selectedDate)

        // Toluene
        val barChartForToluene = findViewById<BarChartView>(R.id.barChartToluene)
        val openPopUpToluene = findViewById<TextView>(R.id.tv_popup_toluene)
        val textViewTolueneMin = findViewById<TextView>(R.id.tv_toluene_min)
        val textViewTolueneMax = findViewById<TextView>(R.id.tv_toluene_max)

        // Acetone
        val barChartForAcetone = findViewById<BarChartView>(R.id.barChartacetone)
        val openPopUpAcetone = findViewById<TextView>(R.id.tv_popup_acetone)
        val textViewAcetoneMin = findViewById<TextView>(R.id.tv_acetone_min)
        val textViewAcetoneMax = findViewById<TextView>(R.id.tv_acetone_max)

        // Ammonia
        val barChartForAmmonia = findViewById<BarChartView>(R.id.barChartammonia)
        val openPopUpAmmonia = findViewById<TextView>(R.id.tv_popup_ammonia)
        val textViewAmmoniaMin = findViewById<TextView>(R.id.tv_ammonia_min)
        val textViewAmmoniaMax = findViewById<TextView>(R.id.tv_ammonia_max)

        // Alcohol
        val barChartForAlcohol = findViewById<BarChartView>(R.id.barChartalcohol)
        val openPopUpAlcohol = findViewById<TextView>(R.id.tv_popup_alcohol)
        val textViewAlcoholMin = findViewById<TextView>(R.id.tv_alcohol_min)
        val textViewAlcoholMax = findViewById<TextView>(R.id.tv_alcohol_max)

        // Hydrogen
        val barChartForHydrogen = findViewById<BarChartView>(R.id.barCharthydrogen)
        val openPopUpHydrogen = findViewById<TextView>(R.id.tv_popup_hydrogen)
        val textViewHydrogenMin = findViewById<TextView>(R.id.tv_hydrogen_min)
        val textViewHydrogenMax = findViewById<TextView>(R.id.tv_hydrogen_max)

        // CO2
        val barChartForCO2 = findViewById<BarChartView>(R.id.barChartdioxide_carbon)
        val openPopUpCO2 = findViewById<TextView>(R.id.tv_popup_dioxide_carbon)
        val textViewCO2Min = findViewById<TextView>(R.id.tv_dioxide_carbon_min)
        val textViewCO2Max = findViewById<TextView>(R.id.tv_dioxide_carbon_max)

        //check if data from arduino in the previous date exists in firebase realtime database
        // if data exists, add the max val in list for bar chart
        // if data exists, add the value 0 in list for bar chart
        databaseReference.child("users").child(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("dataFromArduino")) {

                        if (snapshot.child("dataFromArduino").hasChild(selectedDate)) { // data exist for this user, in this date
                            // Toluene
                            textViewTolueneMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("tolueneMin").value.toString()
                            textViewTolueneMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("tolueneMax").value.toString()
                            previous5ValuesToluene.setCurrent(snapshot.child("dataFromArduino").child(selectedDate)
                                .child("tolueneMax").value.toString().toFloat())

                            // Acetone
                            textViewAcetoneMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("acetoneMin").value.toString()
                            textViewAcetoneMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("acetoneMax").value.toString()
                            previous5ValuesAcetone.setCurrent(snapshot.child("dataFromArduino").child(selectedDate)
                                .child("acetoneMax").value.toString().toFloat())

                            // Ammonia
                            textViewAmmoniaMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("ammoniaMin").value.toString()
                            textViewAmmoniaMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("ammoniaMax").value.toString()
                            previous5ValuesAmmonia.setCurrent(snapshot.child("dataFromArduino").child(selectedDate)
                                .child("ammoniaMax").value.toString().toFloat())

                            // Alcohol
                            textViewAlcoholMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("alcoholMin").value.toString()
                            textViewAlcoholMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("alcoholMax").value.toString()
                            previous5ValuesAlcohol.setCurrent(snapshot.child("dataFromArduino").child(selectedDate)
                                .child("alcoholMax").value.toString().toFloat())///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                            // Hydrogen
                            textViewHydrogenMin.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("hydrogenMin").value.toString()
                            textViewHydrogenMax.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("hydrogenMax").value.toString()
                            previous5ValuesHydrogen.setCurrent(snapshot.child("dataFromArduino").child(selectedDate)
                                .child("hydrogenMax").value.toString().toFloat())

                            // CO2
                            textViewCO2Min.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("dioxideCarbonMin").value.toString()
                            textViewCO2Max.text = snapshot.child("dataFromArduino").child(selectedDate)
                                .child("dioxideCarbonMax").value.toString()

                        }

                        if (snapshot.child("dataFromArduino").hasChild(previousDate1)) { // data exist for this user, in this date
                            //Toluene
                            previous5ValuesToluene.setPrevious1(snapshot.child("dataFromArduino").child(previousDate1)
                                .child("tolueneMax").value.toString().toFloat())
                            // Acetone
                            previous5ValuesAcetone.setPrevious1(snapshot.child("dataFromArduino").child(previousDate1)
                                .child("acetoneMax").value.toString().toFloat())
                            // Ammonia
                            previous5ValuesAmmonia.setPrevious1(snapshot.child("dataFromArduino").child(previousDate1)
                                .child("ammoniaMax").value.toString().toFloat())
                            // Alcohol
                            previous5ValuesAlcohol.setPrevious1(snapshot.child("dataFromArduino").child(previousDate1)
                                .child("alcoholMax").value.toString().toFloat())
                            // Hydrogen
                            previous5ValuesHydrogen.setPrevious1(snapshot.child("dataFromArduino").child(previousDate1)
                                .child("hydrogenMax").value.toString().toFloat())

                        }

                        if (snapshot.child("dataFromArduino").hasChild(previousDate2)) { // data exist for this user, in this date
                            //Toluene
                            previous5ValuesToluene.setPrevious2(snapshot.child("dataFromArduino").child(previousDate2)
                                .child("tolueneMax").value.toString().toFloat())
                            // Acetone
                            previous5ValuesAcetone.setPrevious2(snapshot.child("dataFromArduino").child(previousDate2)
                                .child("acetoneMax").value.toString().toFloat())
                            // Ammonia
                            previous5ValuesAmmonia.setPrevious2(snapshot.child("dataFromArduino").child(previousDate2)
                                .child("ammoniaMax").value.toString().toFloat())
                            // Alcohol
                            previous5ValuesAlcohol.setPrevious2(snapshot.child("dataFromArduino").child(previousDate2)
                                .child("alcoholMax").value.toString().toFloat())
                            // Alcohol
                            previous5ValuesAlcohol.setPrevious2(snapshot.child("dataFromArduino").child(previousDate2)
                                .child("alcoholMax").value.toString().toFloat())
                            // Hydrogen
                            previous5ValuesHydrogen.setPrevious2(snapshot.child("dataFromArduino").child(previousDate2)
                                .child("hydrogenMax").value.toString().toFloat())

                        }

                        if (snapshot.child("dataFromArduino").hasChild(previousDate3)) { // data exist for this user, in this date
                            //Toluene
                            previous5ValuesToluene.setPrevious3(snapshot.child("dataFromArduino").child(previousDate3)
                                .child("tolueneMax").value.toString().toFloat())
                            // Acetone
                            previous5ValuesAcetone.setPrevious3(snapshot.child("dataFromArduino").child(previousDate3)
                                .child("acetoneMax").value.toString().toFloat())
                            // Ammonia
                            previous5ValuesAmmonia.setPrevious3(snapshot.child("dataFromArduino").child(previousDate3)
                                .child("ammoniaMax").value.toString().toFloat())
                            // Alcohol
                            previous5ValuesAlcohol.setPrevious3(snapshot.child("dataFromArduino").child(previousDate3)
                                .child("alcoholMax").value.toString().toFloat())
                            // Hydrogen
                            previous5ValuesHydrogen.setPrevious3(snapshot.child("dataFromArduino").child(previousDate3)
                                .child("hydrogenMax").value.toString().toFloat())

                        }

                        if (snapshot.child("dataFromArduino").hasChild(previousDate4)) { // data exist for this user, in this date
                            //Toluene
                            previous5ValuesToluene.setPrevious4(snapshot.child("dataFromArduino").child(previousDate4)
                                .child("tolueneMax").value.toString().toFloat())
                            // Acetone
                            previous5ValuesAcetone.setPrevious4(snapshot.child("dataFromArduino").child(previousDate4)
                                .child("acetoneMax").value.toString().toFloat())
                            // Ammonia
                            previous5ValuesAmmonia.setPrevious4(snapshot.child("dataFromArduino").child(previousDate4)
                                .child("ammoniaMax").value.toString().toFloat())
                            // Alcohol
                            previous5ValuesAlcohol.setPrevious4(snapshot.child("dataFromArduino").child(previousDate4)
                                .child("alcoholMax").value.toString().toFloat())
                            // Hydrogen
                            previous5ValuesHydrogen.setPrevious4(snapshot.child("dataFromArduino").child(previousDate4)
                                .child("hydrogenMax").value.toString().toFloat())

                        }

                        barChartForToluene.animation.duration = 1000L
                        val barSetToluene = listOf(Pair(previous5Days[4], previous5ValuesToluene.getPrevious4()),
                            Pair(previous5Days[3], previous5ValuesToluene.getPrevious3()),
                            Pair(previous5Days[2], previous5ValuesToluene.getPrevious2()),
                            Pair(previous5Days[1], previous5ValuesToluene.getPrevious1()),
                            Pair(previous5Days[0], previous5ValuesToluene.getCurrent()))
                        barChartForToluene.animate(barSetToluene)

                        barChartForAcetone.animation.duration = 1000L
                        val barSetAcetone = listOf(Pair(previous5Days[4], previous5ValuesAcetone.getPrevious4()),
                            Pair(previous5Days[3], previous5ValuesAcetone.getPrevious3()),
                            Pair(previous5Days[2], previous5ValuesAcetone.getPrevious2()),
                            Pair(previous5Days[1], previous5ValuesAcetone.getPrevious1()),
                            Pair(previous5Days[0], previous5ValuesAcetone.getCurrent()))
                        barChartForAcetone.animate(barSetAcetone)

                        barChartForAmmonia.animation.duration = 1000L
                        val barSetAmmonia = listOf(Pair(previous5Days[4], previous5ValuesAmmonia.getPrevious4()),
                            Pair(previous5Days[3], previous5ValuesAmmonia.getPrevious3()),
                            Pair(previous5Days[2], previous5ValuesAmmonia.getPrevious2()),
                            Pair(previous5Days[1], previous5ValuesAmmonia.getPrevious1()),
                            Pair(previous5Days[0], previous5ValuesAmmonia.getCurrent()))
                        barChartForAmmonia.animate(barSetAmmonia)

                        barChartForAlcohol.animation.duration = 1000L
                        val barSetAlcohol = listOf(Pair(previous5Days[4], previous5ValuesAlcohol.getPrevious4()),
                            Pair(previous5Days[3], previous5ValuesAlcohol.getPrevious3()),
                            Pair(previous5Days[2], previous5ValuesAlcohol.getPrevious2()),
                            Pair(previous5Days[1], previous5ValuesAlcohol.getPrevious1()),
                            Pair(previous5Days[0], previous5ValuesAlcohol.getCurrent()))
                        barChartForAlcohol.animate(barSetAlcohol)

                        barChartForHydrogen.animation.duration = 1000L
                        val barSetHydrogen = listOf(Pair(previous5Days[4], previous5ValuesHydrogen.getPrevious4()),
                            Pair(previous5Days[3], previous5ValuesHydrogen.getPrevious3()),
                            Pair(previous5Days[2], previous5ValuesHydrogen.getPrevious2()),
                            Pair(previous5Days[1], previous5ValuesHydrogen.getPrevious1()),
                            Pair(previous5Days[0], previous5ValuesHydrogen.getCurrent()))
                        barChartForHydrogen.animate(barSetHydrogen)

                        barChartForCO2.animation.duration = 1000L
                        val barSetCO2= listOf(Pair(previous5Days[4], previous5ValuesCO2.getPrevious4()),
                            Pair(previous5Days[3], previous5ValuesCO2.getPrevious3()),
                            Pair(previous5Days[2], previous5ValuesCO2.getPrevious2()),
                            Pair(previous5Days[1], previous5ValuesCO2.getPrevious1()),
                            Pair(previous5Days[0], previous5ValuesCO2.getCurrent()))
                        barChartForCO2.animate(barSetCO2)

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

        //popup buttons
        // Toluene
        openPopUpToluene.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.layout_popup, null)
            window.contentView = view
            val tvInfo = view.findViewById<TextView>(R.id.tv_info)
            tvInfo.text = textPopupToluene
            tvInfo.setOnClickListener {
                window.dismiss()
            }
            window.showAsDropDown(openPopUpToluene)
        }

        // Acetone
        openPopUpAcetone.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.layout_popup, null)
            window.contentView = view
            val tvInfo = view.findViewById<TextView>(R.id.tv_info)
            tvInfo.text = textPopupAcetone
            tvInfo.setOnClickListener {
                window.dismiss()
            }
            window.showAsDropDown(openPopUpAcetone)
        }

        // Ammonia
        openPopUpAmmonia.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.layout_popup, null)
            window.contentView = view
            val tvInfo = view.findViewById<TextView>(R.id.tv_info)
            tvInfo.text = textPopupAmmonia
            tvInfo.setOnClickListener {
                window.dismiss()
            }
            window.showAsDropDown(openPopUpAmmonia)
        }

        // Alcohol
        openPopUpAlcohol.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.layout_popup, null)
            window.contentView = view
            val tvInfo = view.findViewById<TextView>(R.id.tv_info)
            tvInfo.text = textPopupAlcohol
            tvInfo.setOnClickListener {
                window.dismiss()
            }
            window.showAsDropDown(openPopUpAlcohol)
        }

        // Hydrogen
        openPopUpHydrogen.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.layout_popup, null)
            window.contentView = view
            val tvInfo = view.findViewById<TextView>(R.id.tv_info)
            tvInfo.text = textPopupHydrogen
            tvInfo.setOnClickListener {
                window.dismiss()
            }
            window.showAsDropDown(openPopUpHydrogen)
        }

        // CO2
        openPopUpCO2.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.layout_popup, null)
            window.contentView = view
            val tvInfo = view.findViewById<TextView>(R.id.tv_info)
            tvInfo.text = textPopupCO2
            tvInfo.setOnClickListener {
                window.dismiss()
            }
            window.showAsDropDown(openPopUpCO2)
        }

    }

}