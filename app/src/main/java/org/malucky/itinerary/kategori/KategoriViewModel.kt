package org.malucky.itinerary.kategori

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import org.malucky.itinerary.common.constant.Urls
import org.malucky.itinerary.data.ResponseServer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KategoriViewModel : ViewModel() {

    private var _responseprov = MutableLiveData<String>()
    val responseprov : LiveData<String>
        get() = _responseprov
    private var _dataprov = MutableLiveData<List<ResponseServer>>()
    val dataprov : LiveData<List<ResponseServer>>
        get() = _dataprov

    init {

//        _dataprov.value = listOf()
//        Urls.service.getKuliner().enqueue(object : Callback<List<ResponseServer>>{
//            override fun onFailure(call: Call<List<ResponseServer>>, t: Throwable) {
//                _responseprov.value = "Gagal Menampilkan Data"
//                Log.e("Result:",t.message)
//            }
//
//            override fun onResponse(call: Call<List<ResponseServer>>, response: Response<List<ResponseServer>>) {
//                _responseprov.value = "Berhasil Menampilkan Data"
//                _dataprov.value = response.body()
//                Log.e("Result:",response.body().toString())
//            }
//
//        })
    }


}