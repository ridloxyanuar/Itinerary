package org.malucky.itinerary

import io.reactivex.Observable
import org.malucky.itinerary.common.constant.UrlDetails
import org.malucky.itinerary.common.constant.Urls
import org.malucky.itinerary.data.ResponseDetailServer
import org.malucky.itinerary.data.ResponseServer
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Urls.URL_PLACE)
    fun getPlace(): Observable<ResponseServer>

    @GET(Urls.URL_KULINER)
    fun getKuliner(): Observable<ResponseServer>

    @GET(Urls.Params.BASE_URL_PLACE)
    fun getPlace(
        @Query(Urls.Params.QUERY_LOCATION) location: String,
        @Query(Urls.Params.QUERY_RANKBY) rankBy: String,
        @Query(Urls.Params.QUERY_TYPE) type: String,
        @Query(Urls.Params.QUERY_KEY) key: String
    ): Observable<ResponseServer>

    @GET(Urls.Params.BASE_URL_PLACE)
    fun getKuliner(
        @Query(Urls.Params.QUERY_LOCATION) location: String,
        @Query(Urls.Params.QUERY_RANKBY) rankBy: String,
        @Query(Urls.Params.QUERY_TYPE) type: String,
        @Query(Urls.Params.QUERY_KEY) key: String
    ): Observable<ResponseServer>

    @GET(Urls.Params.BASE_URL_PLACE)
    fun getBudaya(
        @Query(Urls.Params.QUERY_LOCATION) location: String,
        @Query(Urls.Params.QUERY_RANKBY) rankBy: String,
        @Query(Urls.Params.QUERY_TYPE) type: String,
        @Query(Urls.Params.QUERY_KEY) key: String
    ): Observable<ResponseServer>

    @GET(Urls.Params.BASE_URL_PLACE)
    fun getJajan(
        @Query(Urls.Params.QUERY_LOCATION) location: String,
        @Query(Urls.Params.QUERY_RANKBY) rankBy: String,
        @Query(Urls.Params.QUERY_TYPE) type: String,
        @Query(Urls.Params.QUERY_KEY) key: String
    ): Observable<ResponseServer>


    @GET(UrlDetails.ParamsDetails.BASE_URL_PLACE_DETAILS)
    fun getPlaceDetails(
        @Query(UrlDetails.ParamsDetails.QUERY_PLACE_ID) place_id: String,
        @Query(UrlDetails.ParamsDetails.QUERY_FIELDS) fields: String,
        @Query(UrlDetails.ParamsDetails.QUERY_KEY_DETAILS) keyDetails: String
    ): Observable<ResponseDetailServer>



}