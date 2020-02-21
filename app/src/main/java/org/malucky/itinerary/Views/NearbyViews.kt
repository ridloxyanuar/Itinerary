package org.malucky.itinerary.Views

import org.malucky.itinerary.data.ResultsItem


interface NearbyViews {
    // jika sukses menampilkan arraylist ke adapter
    fun Success (datas: List<ResultsItem?>)

    fun Error(pesan : String)

}