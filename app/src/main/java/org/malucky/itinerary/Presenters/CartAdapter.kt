package org.malucky.itinerary.Presenters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.item_list_cart.view.*
import kotlinx.android.synthetic.main.place_list_item.view.*
import org.malucky.itinerary.CartActivity
import org.malucky.itinerary.R
import org.malucky.itinerary.TerdekatActivity
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation

class CartAdapter(private val items: List<CartLocation>, private val context: Context) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private lateinit var userDatabase: CartDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_cart, parent, false))

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        holder.bindUI(items[position])
        userDatabase = CartDatabase.getInstance(context)
        val hapus_lokasi = holder.itemView.ib_delete
        hapus_lokasi.setOnClickListener {
            deleted()
        }

    }

    private fun deleted() {
        Completable.fromAction {
            userDatabase!!.cartLocationDatabaseDAO.clear()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    toast("deleted successfully")
                    val intent = Intent(context, CartActivity::class.java)
                    context.startActivity(intent)
                }

                override fun onError(e: Throwable) {
                    toast("deleted invalid")
                }
            })
    }

//    private fun readData() {
//        userDatabase!!.cartLocationDatabaseDAO.getAllLocation().observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe(object : DisposableSingleObserver<List<CartLocation>>() {
//                override fun onSuccess(result: List<CartLocation>) {
//
//                }
//
//                override fun onError(e: Throwable) {
//                    toast("Empty data")
//                }
//            })
//    }

    private fun toast(s: String) {
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show()
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(result: CartLocation) = with(itemView) {

            tv_cart_name.text = result.namaLokasi


        }

    }
}