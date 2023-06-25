package com.example.healthfuelness

import android.graphics.Bitmap

class GalleryItem {

    var icons: Bitmap?= null
    var name: String ?= null
    var date: String ?= null

    constructor(icons: Bitmap?, name: String?, date: String?) {
        this.icons = icons
        this.name = name
        this.date = date
    }
}