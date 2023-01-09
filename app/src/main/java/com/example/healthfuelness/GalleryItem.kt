package com.example.healthfuelness

class GalleryItem {

    var icons: Int ?= 0
    var name: String ?= null
    var date: String ?= null

    constructor(icons: Int?, name: String?, date: String?) {
        this.icons = icons
        this.name = name
        this.date = date
    }
}