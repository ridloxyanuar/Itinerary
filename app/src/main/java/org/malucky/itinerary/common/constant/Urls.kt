package org.malucky.itinerary.common.constant

import org.malucky.itinerary.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Urls {
    val SERVER_DEVELOP = "https://maps.googleapis.com/maps/api/place/nearbysearch/"

    val retrofit = Retrofit.Builder()
        .baseUrl(SERVER_DEVELOP)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val service = retrofit.create<ApiService>(ApiService::class.java!!)

}