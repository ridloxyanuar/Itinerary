package org.malucky.itinerary.common.constant

import org.malucky.itinerary.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object UrlDetails {

    object ParamsDetails {

        const val QUERY_PLACE_ID = "place_id"
        const val QUERY_FIELDS = "fields"
        const val QUERY_KEY_DETAILS = "key"

        const val BASE_URL_PLACE_DETAILS = "json?"
    }

    const val URL_PLACE_DETAILS = "json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=opening_hours&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c"

    val SERVER_DEVELOP_DETAILS = "https://maps.googleapis.com/maps/api/place/details/"

    val retrofitDetails = Retrofit.Builder()
        .baseUrl(UrlDetails.SERVER_DEVELOP_DETAILS)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val serviceDetails = retrofitDetails.create<ApiService>(ApiService::class.java!!)
}