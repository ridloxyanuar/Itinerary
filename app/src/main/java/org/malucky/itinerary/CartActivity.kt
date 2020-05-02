package org.malucky.itinerary

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_terdekat.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.malucky.itinerary.Presenters.CartAdapter
import org.malucky.itinerary.Views.CartCallback
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation
import org.malucky.itinerary.db.CartLocationDAO
import org.malucky.itinerary.itinerary.ItineraryFragment
import org.malucky.itinerary.util.bottomSheetConfirmationDialog
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : BaseActivity() {

    private var mainAd: CartAdapter? = null
    private var userDatabase: CartDatabase? = null
    private lateinit var cartLocation: ArrayList<CartLocation>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun getView() = R.layout.activity_cart

    override fun onActivityCreated() {
        var status = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (status == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    val lati = location?.latitude
                    val lng = location?.longitude

                    val geocoder = Geocoder(this, Locale.getDefault())
                    val listAddress : List<Address> = geocoder.getFromLocation(lati!!, lng!!, 1)

                    if (listAddress.size > 0){
                        val address : Address = listAddress.get(0)
                        val hasil = address.getAddressLine(0)

                        textView8.text = hasil
                    }else{
                        textView8.text = "Lokasi Tidak ditemukan"
                    }

                }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                123
            )
        }


        toolbar_cart.setTitle("Atur Perjalanan")
        setSupportActionBar(toolbar_terdekat)

        toolbar_cart.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_cart.setNavigationOnClickListener {
            finish()
        }
        cartLocation = ArrayList()
        userDatabase = CartDatabase.getInstance(this)
        readData()

        txt_tambah_cart.setOnClickListener {
            finish()
        }

        imageButton.setOnClickListener {
            bottomSheetConfirmationDialog("Pilih Lokasi")
        }
    }

    private fun setupNavigatePenjalanan(listData: List<CartLocation>) {
        btn_atur_perjalanan.setOnClickListener {
            val intent = Intent(this@CartActivity, AturActivity::class.java)
            val stringListCartLocation = Gson().toJson(listData)
            intent.putExtra("EXTRA_NAME", stringListCartLocation)

            startActivity(intent)
        }
    }

    private fun setupAdapter(result: List<CartLocation>) {
        mainAd = CartAdapter(result, this@CartActivity, object : CartCallback {
            override fun onItemClick(listData: List<CartLocation>) {
                setupNavigatePenjalanan(listData)
            }
        })
        rv_cart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAd
        }

        rv_cart.setNestedScrollingEnabled(false)
    }


    private fun readData() {
        userDatabase!!.cartLocationDatabaseDAO.getAllLocation()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<List<CartLocation>>() {
                override fun onSuccess(result: List<CartLocation>) {
                    setupAdapter(result)

                }

                override fun onError(e: Throwable) {
                    toast("Empty data")
                }
            })
    }


    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
