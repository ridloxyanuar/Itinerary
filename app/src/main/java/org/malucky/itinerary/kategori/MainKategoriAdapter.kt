//package org.malucky.itinerary.kategori
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.Color
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.MultiTransformation
//import com.bumptech.glide.load.resource.bitmap.CenterCrop
//import io.reactivex.Completable
//import io.reactivex.CompletableObserver
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.Disposable
//import io.reactivex.schedulers.Schedulers
//import jp.wasabeef.glide.transformations.RoundedCornersTransformation
//import kotlinx.android.synthetic.main.place_list_item.view.*
//import org.malucky.itinerary.CartActivity
//import org.malucky.itinerary.R
//import org.malucky.itinerary.data.ResponseServer
//import org.malucky.itinerary.data.ResultsItem
//import org.malucky.itinerary.db.CartDatabase
//import org.malucky.itinerary.db.CartLocation
//import org.malucky.itinerary.util.haversine
//
//class MainKategoriAdapter(private val attributes : List<ResponseServer>) : RecyclerView.Adapter<MainKategoriAdapter.ListViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainKategoriAdapter.ListViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return MainKategoriAdapter.ListViewHolder(inflater, parent)
//    }
//
//    override fun getItemCount(): Int = attributes.size
//
//    override fun onBindViewHolder(holder: MainKategoriAdapter.ListViewHolder, position: Int) {
//        val item = attributes.get(position)
//        holder.bind(item)
//    }
//
//    class ListViewHolder (inflater : LayoutInflater, parent: ViewGroup):
//        RecyclerView.ViewHolder(inflater.inflate(R.layout.place_list_item,parent,false)){
//        private var tvNamaTempat : TextView? = null
//        private var tvAlamatTempat : TextView? = null
//        private var tvRatingTempat : TextView? = null
//        private var tvOpenHours : TextView? = null
////        private var tvMeninggal : TextView? = null
//        init {
//            tvNamaTempat = itemView.findViewById(R.id.txt_namaTempat)
//            tvAlamatTempat = itemView.findViewById(R.id.txt_alamtTempat)
//            tvRatingTempat = itemView.findViewById(R.id.txt_ratingTempat)
//            tvOpenHours = itemView.findViewById(R.id.txt_open_hours)
////            tvMeninggal = itemView.findViewById(R.id.list_provinsi_mati)
//        }
//        fun bind(item: ResponseServer) {
//            val positif = item.results?.get(
//            val sembuh = item.attributes?.kasusSemb?.toInt()
//            val mati = item.attributes?.kasusMeni?.toInt()
//            val hasil = positif!! - sembuh!! - mati!!
//            tvProvinsi?.text = item.attributes?.provinsi
//            tvkasus?.text = positif.toString() + " Kasus"
//            tvPositif?.text = hasil.toString()
//            tvSembuh?.text = sembuh.toString()
//            tvMeninggal?.text = mati.toString()
//
//        }
//
//    }
//
//}
