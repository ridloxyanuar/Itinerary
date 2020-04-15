package org.malucky.itinerary.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel


@Entity(tableName = "cartLocation")
class CartLocation{

    @PrimaryKey(autoGenerate = true)
    var locId: Int = 0

    @ColumnInfo(name = "nama_lokasi")
    var namaLokasi: String = ""

    @ColumnInfo(name = "latitude")
    var lat: String = ""

    @ColumnInfo(name = "longitude")
    var lng: String = ""


    constructor(locId: Int, namaLokasi: String, lat: String, lng: String) {
        this.locId = locId
        this.namaLokasi = namaLokasi
        this.lat = lat
        this.lng = lng
    }

    @Ignore
    constructor(namaLokasi: String, lat: String, lng: String) {
        this.namaLokasi = namaLokasi
        this.lat = lat
        this.lng = lng
    }


}





