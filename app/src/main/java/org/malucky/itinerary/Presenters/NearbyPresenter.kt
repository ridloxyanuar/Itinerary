package org.malucky.itinerary.Presenters


interface NearbyPresenter {

    fun getData(lat:String,lng:String)
    fun getDataKuliner(lat:String,lng:String)
    fun getDataBudaya(lat:String,lng:String)
    fun getDataJajan(lat:String,lng:String)

    //details_place
    fun getPlaceDetails(placeId: String)
}