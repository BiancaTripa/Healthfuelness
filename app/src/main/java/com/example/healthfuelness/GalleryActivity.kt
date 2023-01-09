package com.example.healthfuelness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast

class GalleryActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var gridView: GridView ?= null
    private var arrayList: ArrayList<GalleryItem> ?= null
    private var galleryAdapter: GalleryAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        gridView = findViewById(R.id.grid_view)
        arrayList = ArrayList()
        arrayList = setDataList()
        galleryAdapter = GalleryAdapter(applicationContext, arrayList!!)
        gridView?.adapter = galleryAdapter
        gridView?.onItemClickListener = this
    }

    private fun setDataList(): ArrayList<GalleryItem> {

        var arrayList: ArrayList<GalleryItem> = ArrayList()

        arrayList.add(GalleryItem(R.drawable.ic_camera, "Camera", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_logo, "Logo", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_map, "Map", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_user, "User", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_message, "Message", "date"))
        arrayList.add(GalleryItem(R.drawable.ic_password, "Password", "date"))

        return arrayList
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var galleryItem: GalleryItem = arrayList!![p2]
        Toast.makeText(applicationContext, galleryItem.name, Toast.LENGTH_LONG).show()
    }
}