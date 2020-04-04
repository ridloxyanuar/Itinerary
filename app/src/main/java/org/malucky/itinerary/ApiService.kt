package org.malucky.itinerary

import io.reactivex.Observable
import org.malucky.itinerary.data.ResponseServer
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    // TODO 2 buat api service.kt
    // request to Server
    @GET("json?location=-6.9150381,107.6186398&rankby=distance&type=tourist_attraction&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
    fun getPlace(): Observable<ResponseServer>

}