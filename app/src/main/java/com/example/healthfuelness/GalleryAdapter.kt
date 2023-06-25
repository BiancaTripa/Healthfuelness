package com.example.healthfuelness

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GalleryAdapter(var context: Context, var arrayList: ArrayList<GalleryItem>): BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view: View = View.inflate(context, R.layout.grid_item_list, null)

        var icons: ImageView = view.findViewById(R.id.icons)
        var names: TextView = view.findViewById(R.id.name_text_view)
        var dates: TextView = view.findViewById(R.id.date_view)

        var galleryItem: GalleryItem = arrayList[p0]

        icons.setImageBitmap(galleryItem.icons!!)
        names.text = galleryItem.name
        dates.text = galleryItem.date

        return view
    }
}