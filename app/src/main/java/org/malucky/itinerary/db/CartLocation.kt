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

    @ColumnInfo(name = "gambar_lokasi")
    var gambarLokasi: String = ""

    @ColumnInfo(name = "nama_lokasi")
    var namaLokasi: String = ""

    @ColumnInfo(name = "latitude")
    var latitude: String = ""

    @ColumnInfo(name = "longitude")
    var longitude: String = ""

    @ColumnInfo(name = "jarak")
    var jarak: String = ""


    constructor(locId: Int, gambarLokasi: String, namaLokasi: String, latitude: String, longitude: String, jarak: String) {
        this.locId = locId
        this.gambarLokasi = gambarLokasi
        this.namaLokasi = namaLokasi
        this.latitude = latitude
        this.longitude = longitude
        this.jarak = jarak
    }

    @Ignore
    constructor(gambarLokasi: String, namaLokasi: String, latitude: String, longitude: String, jarak: String) {
        this.gambarLokasi = gambarLokasi
        this.namaLokasi = namaLokasi
        this.latitude = latitude
        this.longitude = longitude
        this.jarak = jarak
    }


}





