package org.malucky.itinerary.Presenters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail_location.*
import kotlinx.android.synthetic.main.item_list_cart.view.*
import org.malucky.itinerary.R
import org.malucky.itinerary.Views.CartCallback
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation
import java.util.*
import kotlin.Comparator

class CartAdapter(
    private val items: List<CartLocation>,
    private val context: Context,
    private val callback: CartCallback
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    init {
        sortData()
    }

    private lateinit var userDatabase: CartDatabase


    private fun sortData() {

        val tempListData = mutableListOf<CartLocation>()

        tempListData.addAll(items)
        Collections.sort(tempListData, object : Comparator<CartLocation> {
            override fun compare(o1: CartLocation?, o2: CartLocation?): Int {
                return (cleaningJarak(o1?.jarak!!) - cleaningJarak(o2?.jarak!!))
            }
        })
        callback.onItemClick(tempListData)
    }

    private fun cleaningJarak(jarak: String): Int {
        return jarak.replace(" m", "").toInt()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_cart,
                parent,
                false
            )
        )

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        holder.bindUI(items[position])
        userDatabase = CartDatabase.getInstance(context)
        val hapus_lokasi = holder.itemView.ib_delete
        hapus_lokasi.setOnClickListener {
            deleted(position)
            notifyItemRemoved(position)
        }

    }

    private fun deleted(position: Int) {
        Completable.fromAction {
            userDatabase!!.cartLocationDatabaseDAO.deleteID(items.get(position))
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    toast("deleted successfully")
                    val i = items.get(position)
                    notifyItemRemoved(position)
                }

                override fun onError(e: Throwable) {
                    toast("deleted invalid")
                }
            })
    }

    private fun toast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(result: CartLocation) = with(itemView) {

            Glide.with(this)
                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+result.gambarLokasi+"&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
                .transform(MultiTransformation(CenterCrop(), RoundedCornersTransformation(10,   0)))
                .error(R.drawable.noimage)
                .into(iv_cart)
            tv_cart_name.text = result.namaLokasi
            txt_location.text = result.jarak


        }
    }


}
