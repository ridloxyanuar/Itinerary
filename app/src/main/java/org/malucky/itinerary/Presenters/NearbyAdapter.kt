package org.malucky.itinerary.Presenters

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import kotlinx.android.synthetic.main.place_list_item.view.*
import org.malucky.itinerary.CartActivity
import org.malucky.itinerary.R
import org.malucky.itinerary.data.ResultsItem
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation


class NearbyAdapter(data: List<ResultsItem?>,var onClickListener: OnLocationItemClickListner) : RecyclerView.Adapter<NearbyAdapter.MyHolder>() {
     var ambilData : List<ResultsItem?>
     lateinit var context: Context
    init {
        this.ambilData = data
    }
    private lateinit var userDatabase: CartDatabase


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.place_list_item, parent,false)
        context = parent.context
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return ambilData.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(ambilData, position,onClickListener)
        val poto = holder.itemView.iv_tempat
        val add_location = holder.itemView.button3

        Glide.with(context)
            .load(ambilData.get(position)?.icon)
            .transform(MultiTransformation(CenterCrop(), RoundedCornersTransformation(25,0)))
            .into(poto)

        userDatabase = CartDatabase.getInstance(context)

        add_location.setOnClickListener {
            inserta(ambilData.get(position)?.name.toString())
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }

        holder.itemView.setOnClickListener{
            holder.bind(ambilData,position,onClickListener)
        }

    }

    private fun inserta(name: String) {
        Completable.fromAction { val data = CartLocation(name)
            userDatabase.cartLocationDatabaseDAO.insert(data)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    toast("Data store successfully")
                }

                override fun onError(e: Throwable) {
                    toast("Data not store")
                }
            })
    }

    private fun toast(s: String) {
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show()
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ambilData: List<ResultsItem?>, position: Int, action:OnLocationItemClickListner) {
//            itemView.iv_tempat.setImageResource(position) = ambilData.get(position)?.photos
//            Glide.with()
//                .load(ambilData.get(position)?.photos)
//                .into(itemView.iv_tempat)
            itemView.txt_namaTempat.text = ambilData.get(position)?.name
            itemView.txt_alamtTempat.text = ambilData.get(position)?.vicinity
            itemView.txt_ratingTempat.text = ambilData.get(position)?.rating.toString() + " Rating"
            if (itemView.txt_ratingTempat.text.equals("null Rating")){
                itemView.txt_ratingTempat.text = "Tidak Ada Rating"
            }
            itemView.txt_open_hours.text = ambilData.get(position)?.openingHours?.openNow.toString()
            if (itemView.txt_open_hours.text.equals("false")){
                itemView.txt_open_hours.text = "Tutup"
                itemView.txt_open_hours.setTextColor(Color.parseColor("#F44336"))
            }else if (itemView.txt_open_hours.text.equals("true")){
                itemView.txt_open_hours.text = "Buka"
                itemView.txt_open_hours.setTextColor(Color.parseColor("#4CAF50"))
            }else{
                itemView.txt_open_hours.text = "-"
                itemView.txt_open_hours.setTextColor(Color.parseColor("#000000"))

            }

            itemView.setOnClickListener {
                action.onItemClick(ambilData, adapterPosition)
            }
        }

    }

    interface OnLocationItemClickListner{
        fun onItemClick(item: List<ResultsItem?>, position: Int)
    }


}
