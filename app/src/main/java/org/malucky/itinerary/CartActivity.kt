package org.malucky.itinerary

import android.content.Intent
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_terdekat.*
import org.malucky.itinerary.Presenters.CartAdapter
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation
import org.malucky.itinerary.util.bottomSheetConfirmationDialog
import org.malucky.itinerary.util.haversine
import java.util.*
import kotlin.math.min


class CartActivity : BaseActivity() {

    private lateinit var trya: String
    private var aturNama: String? = null
    private var atur: String? = null
    private var dataLokasi: String? = null
    private var havLoga: Double? = null
    private var mainAd: CartAdapter? = null
    private var latitudeLokasi: String? = null
    private var namaLokasi: String? = null
    private var longitudeLokasi: String? = null
    private var userDatabase: CartDatabase? = null


    override fun getView() = R.layout.activity_cart

    override fun onActivityCreated() {
        toolbar_cart.setTitle("Atur Perjalanan")
        setSupportActionBar(toolbar_terdekat)

        toolbar_cart.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_cart.setNavigationOnClickListener {
            finish()
        }
        userDatabase = CartDatabase.getInstance(this)
        readData()

        txt_tambah_cart.setOnClickListener {
            navigate.mainActivity(this)
        }

        btn_atur_perjalanan.setOnClickListener {
//            for (i in dataLokasi.toString()){
//                i.let {
//                    toast("" + it)
//                    toast(namaLokasi+ haversine(-6.9150381,107.6186398, latitudeLokasi!!.toDouble(), longitudeLokasi!!.toDouble())+ " km")
//                }
//            }
//            toast(""+namaLokasi)
//            toast(""+ haversine(-6.9150381,107.6186398, latitudeLokasi!!.toDouble(), longitudeLokasi!!.toDouble())+ " km")

            val intent = Intent(this, AturActivity::class.java)
            intent.putExtra("EXTRA_NAMA", aturNama)
            intent.putExtra("EXTRA_DISTANCE", atur)
            startActivity(intent)

        }

        imageButton.setOnClickListener {
            bottomSheetConfirmationDialog("Pilih Lokasi")

//            toast(distance.toString() + " km")

            toast(""+ haversine(-6.9150381,107.6186398, -6.343773, 106.781054)+ " km")

            val result : List<CartLocation>

//            for ( in result){
//                Log.i("lokasi", i.toString())
//            }

        }
    }

    private fun readData() {
        userDatabase!!.cartLocationDatabaseDAO.getAllLocation().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<List<CartLocation>>() {
                override fun onSuccess(result: List<CartLocation>) {
                     mainAd = CartAdapter(result, this@CartActivity)
                    rv_cart.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = mainAd
                        mainAd!!.notifyDataSetChanged()
                    }
                    rv_cart.setNestedScrollingEnabled(false)


                    for (data in result){
                        data.let {
                             namaLokasi = it.namaLokasi
                             latitudeLokasi = it.lat
                             longitudeLokasi = it.lng

                             havLoga = haversine(-6.9150381,107.6186398, latitudeLokasi!!.toDouble() , longitudeLokasi!!.toDouble())
//                            toast(namaLokasi+latitudeLokasi+longitudeLokasi)
//                                toast(namaLokasi+ havLoga +" km")

                            val hasilHaversine = doubleArrayOf(havLoga!!.toDouble() * 1000)

                            val hasil = namaLokasi+ havLoga!!.toDouble() + " km"

                            for (i in hasilHaversine.indices) {
//                                    toast("Ascending : "+hasilHaversine.sortedArray()[i] + " m")
                                    aturNama = namaLokasi.toString()
                                    atur = "" + hasilHaversine.sortedDescending()[i] + " m"
                                    toast(aturNama + atur)
                                Log.d(aturNama, atur)

                                mainAd?.notifyDataSetChanged()

                                trya = aturNama + atur


                            }
                        }
                        dataLokasi = namaLokasi + havLoga.toString() + " km"
//                        toast(dataLokasi.toString())
                    }
                    toast(""+result.size)
                }

                override fun onError(e: Throwable) {
                    toast("Empty data")
                }
            })
    }


    private fun toast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }


}
