package org.malucky.itinerary.data

class Settings {
    var icons : Int ? = 0
    var judul : String ? = null
    var ket : String ? = null

    constructor(icons: Int?, judul: String?,ket: String?) {
        this.icons = icons
        this.judul = judul
        this.ket = ket
    }
}