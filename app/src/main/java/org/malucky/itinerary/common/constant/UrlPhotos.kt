package org.malucky.itinerary.common.constant

import org.malucky.itinerary.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object UrlPhotos {

    object ParamsPhotos {

        const val QUERY_MAXWIDTH = "maxwidth"
        const val QUERY_PHOTOREF = "photoreference"
        const val QUERY_KEY_PHOTO = "key"

        const val BASE_URL_PLACE_PHOTO = "photo?"
    }

    const val URL_PLACE_PHOTO = "photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c"

    val SERVER_DEVELOP_PHOTOS = "https://maps.googleapis.com/maps/api/place/"

    val retrofitPhotos = Retrofit.Builder()
        .baseUrl(UrlPhotos.SERVER_DEVELOP_PHOTOS)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val servicePhotos = retrofitPhotos.create<ApiService>(ApiService::class.java!!)
}