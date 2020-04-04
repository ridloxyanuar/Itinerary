package org.malucky.itinerary

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_terdekat.*
import org.malucky.itinerary.Presenters.CartAdapter
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation

class CartActivity : BaseActivity() {

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
    }

    private fun readData() {
        userDatabase!!.cartLocationDatabaseDAO.getAllLocation().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<List<CartLocation>>() {
                override fun onSuccess(result: List<CartLocation>) {
                    val mainAd = CartAdapter(result, this@CartActivity)
                    rv_cart.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = mainAd
                    }
                    rv_cart.setNestedScrollingEnabled(false)
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
