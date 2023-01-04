package com.example.healthfuelness

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter (private val context: Activity, private val arrayList: ArrayList<HappyPhoto>) : ArrayAdapter<HappyPhoto>(context, R.layout.list_item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item, null)

        val imageView: ImageView = view.findViewById(R.id.iv_happy_pic)
        val description: TextView = view.findViewById(R.id.tv_short_description)
        val date: TextView = view.findViewById(R.id.tv_date)

        imageView.setImageResource(arrayList[position].imageId)
        description.text = arrayList[position].description
        date.text = arrayList[position].date

        return view
    }

}