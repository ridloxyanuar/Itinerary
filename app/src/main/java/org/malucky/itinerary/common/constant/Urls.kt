package org.malucky.itinerary.common.constant

import org.malucky.itinerary.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Urls {

    object Params {

        const val QUERY_LOCATION = "location"
        const val QUERY_RANKBY = "rankby"
        const val QUERY_TYPE = "type"
        const val QUERY_KEY = "key"

        const val BASE_URL_PLACE = "json?"
    }

    const val URL_PLACE =
        "json?location=-6.9150381,107.6186398&rankby=distance&type=tourist_attraction&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c"
    const val URL_KULINER =
        "json?location=-6.9150381,107.6186398&rankby=distance&type=restaurant&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c"

    val SERVER_DEVELOP = "https://maps.googleapis.com/maps/api/place/nearbysearch/"

    val retrofit = Retrofit.Builder()
        .baseUrl(SERVER_DEVELOP)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val service = retrofit.create<ApiService>(ApiService::class.java!!)

}