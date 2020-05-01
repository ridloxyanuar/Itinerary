package org.malucky.itinerary.Presenters

import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.malucky.itinerary.MainActivity
import org.malucky.itinerary.Views.LastLocation
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.common.constant.Urls
import org.malucky.itinerary.data.ResponseServer


class NearbyPresenterImp : NearbyPresenter{


    var nearbyViews : NearbyViews? = null


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

    constructor(nearbyViews: NearbyViews?) {
        this.nearbyViews = nearbyViews
    }


}