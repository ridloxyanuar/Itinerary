package org.malucky.itinerary

import io.reactivex.Observable
import org.malucky.itinerary.common.constant.UrlPhotos
import org.malucky.itinerary.common.constant.Urls
import org.malucky.itinerary.data.ResponseServer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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

    @GET(UrlPhotos.ParamsPhotos.BASE_URL_PLACE_PHOTO)
    fun getPhoto(
        @Query(UrlPhotos.ParamsPhotos.QUERY_MAXWIDTH) maxwidth: String,
        @Query(UrlPhotos.ParamsPhotos.QUERY_PHOTOREF) photoref: String,
        @Query(UrlPhotos.ParamsPhotos.QUERY_KEY_PHOTO) keyPhoto: String
    ): Observable<ResponseServer>



}