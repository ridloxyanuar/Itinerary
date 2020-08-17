package org.malucky.itinerary

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_terdekat.*
import org.malucky.itinerary.Presenters.CartAdapter
import org.malucky.itinerary.Views.CartCallback
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation
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
            bottomSheetConfirmationDialog("Detail Lokasi", textView8.text.toString())
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


    private fun readData() {
        userDatabase!!.cartLocationDatabaseDAO.getAllLocation()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<List<CartLocation>>() {
                override fun onSuccess(result: List<CartLocation>) {
//                    setupAdapter(result)
                    setupFrogoRecycler(result)
                }

                override fun onError(e: Throwable) {
                    toast("Empty data")
                }
            })
    }

    private fun setupFrogoRecycler(result: List<CartLocation>) {
        setupNavigatePenjalanan(result)
        val adapterCallback = object : FrogoAdapterCallback<CartLocation> {
            override fun setupInitComponent(view: View, data: CartLocation) {
//
                Glide.with(this@CartActivity)
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+data.gambarLokasi+"&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
                    .transform(MultiTransformation(CenterCrop(), RoundedCornersTransformation(10,   0)))
                    .error(R.drawable.noimage)
                    .into(view.findViewById<ImageView>(R.id.iv_cart))

                view.findViewById<TextView>(R.id.tv_cart_name).text = data.namaLokasi
                view.findViewById<TextView>(R.id.txt_location).text = data.jarak
                view.findViewById<ImageButton>(R.id.ib_delete).setOnClickListener {
                    val id = data.locId
                    deleted(id)
                }
            }

            override fun onItemClicked(data: CartLocation) {

            }

            override fun onItemLongClicked(data: CartLocation) {

            }
        }

        rv_cart.injector<CartLocation>()
            .addData(result)
            .addCustomView(R.layout.item_list_cart)
            .addEmptyView(null)
            .addCallback(adapterCallback)
            .createLayoutLinearVertical(false)
            .createAdapter()
            .build(rv_cart)
    }

    private fun deleted(locId: Long) {
        Completable.fromAction {
            userDatabase!!.cartLocationDatabaseDAO.deleteByItemId(locId)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.single())
            .subscribe(object : CompletableObserver{
                override fun onComplete() {
                    toast("deleted successfully")
                    readData()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    toast("deleted invalid")
                }

            })

    }



    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
