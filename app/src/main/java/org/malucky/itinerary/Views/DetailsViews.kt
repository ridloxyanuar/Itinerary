package org.malucky.itinerary.Views

import org.malucky.itinerary.data.ResultsDetailsItem


interface DetailsViews {

    fun Success (datas: ResultsDetailsItem)

    fun Error(pesan : String)

}