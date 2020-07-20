package org.malucky.itinerary.Presenters

import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.malucky.itinerary.MainActivity
import org.malucky.itinerary.Views.DetailsViews
import org.malucky.itinerary.Views.LastLocation
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.common.constant.UrlDetails
import org.malucky.itinerary.common.constant.Urls
import org.malucky.itinerary.data.ResponseDetailServer
import org.malucky.itinerary.data.ResponseServer


class NearbyPresenterImp : NearbyPresenter{


    var nearbyViews : NearbyViews? = null
    var detailsViews : DetailsViews? = null


    override fun getData(lat:String,lng:String) {
            Urls.service
                .getPlace(lat+","+lng,"distance", "tourist_attraction", "AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                        t: ResponseServer? ->

                    var result = t?.results
                    nearbyViews?.Success(result!!)
                    Log.d("data", t?.results.toString())
                }, {
                        e -> e.localizedMessage
                })


    }

    override fun getDataPopuler(lat:String,lng:String) {
        Urls.service
            .getPlace(lat+","+lng,"prominence", "tourist_attraction", "AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    t: ResponseServer? ->

                var result = t?.results
                nearbyViews?.Success(result!!)
                Log.d("data", t?.results.toString())
            }, {
                    e -> e.localizedMessage
            })


    }

    override fun getDataKuliner(lat:String,lng:String) {
        Urls.service
            .getKuliner(lat+","+lng,"distance", "restaurant", "AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    t: ResponseServer? ->

                var result = t?.results
                nearbyViews?.Success(result!!)
                Log.d("data", t?.results.toString())
            }, {
                    e -> e.localizedMessage
            })
    }

    override fun getDataBudaya(lat:String,lng:String) {
        Urls.service
            .getBudaya(lat+","+lng,"distance", "art_gallery", "AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    t: ResponseServer? ->

                var result = t?.results
                nearbyViews?.Success(result!!)
                Log.d("data", t?.results.toString())
            }, {
                    e -> e.localizedMessage
            })
    }

    override fun getDataJajan(lat:String,lng:String) {
        Urls.service
            .getJajan(lat+","+lng,"distance", "bakery", "AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    t: ResponseServer? ->

                var result = t?.results
                nearbyViews?.Success(result!!)
                Log.d("data", t?.results.toString())
            }, {
                    e -> e.localizedMessage
            })
    }

    override fun getDataHotel(lat: String, lng: String) {
        Urls.service
            .getHotel(lat+","+lng,"distance", "car_rental", "AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    t: ResponseServer? ->

                var result = t?.results
                nearbyViews?.Success(result!!)
                Log.d("data", t?.results.toString())
            }, {
                    e -> e.localizedMessage
            })
    }

    override fun getDataATM(lat: String, lng: String) {
        Urls.service
            .getHotel(lat+","+lng,"distance", "gas_station", "AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    t: ResponseServer? ->

                var result = t?.results
                nearbyViews?.Success(result!!)
                Log.d("data", t?.results.toString())
            }, {
                    e -> e.localizedMessage
            })
    }

    override fun getPlaceDetails(placeId: String) {
        UrlDetails.serviceDetails
            .getPlaceDetails(placeId, "opening_hours", "AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                t: ResponseDetailServer? ->

                var result = t?.results
                detailsViews?.Success(result!!)
                Log.d("details", t?.results.toString())
            },{
                e -> e.localizedMessage
            })
    }

    constructor(nearbyViews: NearbyViews?) {
        this.nearbyViews = nearbyViews
    }

    constructor(detailsViews: DetailsViews) {
        this.detailsViews = detailsViews
    }


}