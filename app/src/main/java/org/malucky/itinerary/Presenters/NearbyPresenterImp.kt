package org.malucky.itinerary.Presenters

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.malucky.itinerary.Presenters.NearbyAdapter
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.common.constant.Urls
import org.malucky.itinerary.data.ResponseServer
import org.malucky.itinerary.data.ResultsItem

/**
 * Created by DevJurnal on 2/18/18.
 */
class NearbyPresenterImp : NearbyPresenter{

    var nearbyViews : NearbyViews? = null



    override fun getData() {
        Urls.service
                .getPlace()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                   t: ResponseServer? ->

                    var result = t?.results
//                    var adapter = NearbyAdapter(result!!,this)
                    nearbyViews?.Success(result!!)
//                    recyclerView.layoutManager = LinearLayoutManager(applicationContext)
//                    recyclerView.adapter = adapter
                    Log.d("data", t?.results.toString())
                }, {
                    e -> e.localizedMessage
                })
    }

    constructor(nearbyViews: NearbyViews?) {
        this.nearbyViews = nearbyViews
    }


//    override fun addList(result: List<ResultsItem?>) {
//        results.add(result)
//
//        nearbyViews?.Success(results)
//    }


}