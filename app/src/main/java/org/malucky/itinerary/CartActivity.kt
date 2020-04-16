package org.malucky.itinerary

import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_terdekat.*
import org.malucky.itinerary.Presenters.CartAdapter
import org.malucky.itinerary.Views.CartCallback
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation
import org.malucky.itinerary.db.CartLocationDAO
import org.malucky.itinerary.util.bottomSheetConfirmationDialog
import kotlin.collections.ArrayList


// cobain dlo run
//oke bentar bang
//bisa bang, berati data nya tinggal send aja?
/// YOIIII
//// udah ya gw out
//oke thanks bang

class CartActivity : BaseActivity() {

    private var mainAd: CartAdapter? = null
    private var userDatabase: CartDatabase? = null
    private lateinit var cartLocation: ArrayList<CartLocation>

    override fun getView() = R.layout.activity_cart

    override fun onActivityCreated() {
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
            navigate.mainActivity(this)
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

            // ini terlalu banyak masuk
            // gw bantuin keluarin 1 1 ya
            //oke bang

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
            adapter!!.notifyDataSetChanged()
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
