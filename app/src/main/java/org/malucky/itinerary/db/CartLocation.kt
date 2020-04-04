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

    constructor(locId: Int, namaLokasi: String) {
        this.locId = locId
        this.namaLokasi = namaLokasi
    }

    @Ignore
    constructor(namaLokasi: String) {
        this.namaLokasi = namaLokasi
    }


}





