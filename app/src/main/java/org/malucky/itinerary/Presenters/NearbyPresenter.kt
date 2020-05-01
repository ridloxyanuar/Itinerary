package org.malucky.itinerary.Presenters


interface NearbyPresenter {
//    fun addList(result: List<ResultsItem?>?)

    fun getData(lat:String,lng:String)
    fun getDataKuliner(lat:String,lng:String)
}