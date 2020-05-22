package org.malucky.itinerary.Presenters

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_list_cart.view.*
import kotlinx.android.synthetic.main.place_list_item.view.*
import org.malucky.itinerary.CartActivity
import org.malucky.itinerary.R
import org.malucky.itinerary.data.ResultsItem
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation
import org.malucky.itinerary.util.haversine


class NearbyAdapter(data: List<ResultsItem?>,var onClickListener: OnLocationItemClickListner) : RecyclerView.Adapter<NearbyAdapter.MyHolder>() {
    private lateinit var jarak: String
    var ambilData : List<ResultsItem?>
     lateinit var context: Context
    init {
        this.ambilData = data
    }
    private lateinit var userDatabase: CartDatabase
    private lateinit var fusedLocationClient: FusedLocationProviderClient



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
        val placeId  = ambilData.get(position)?.placeId
        val photoRef = ambilData.get(position)?.photos?.get(0)?.photoReference


        Glide.with(context)
            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photoRef+"&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .transform(MultiTransformation(CenterCrop(), RoundedCornersTransformation(25,0)))
            .error(R.drawable.noimage)
            .into(poto)


        userDatabase = CartDatabase.getInstance(context)

        //ini fungsi tombolnya
        add_location.setOnClickListener{

            var status = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            if (status == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        val lati = location?.latitude
                        val lng = location?.longitude

                        val latitude = ambilData.get(position)?.geometry?.location?.lat.toString()
                        val longitude = ambilData.get(position)?.geometry?.location?.lng.toString()

                        val havLoga = haversine(lati!!.toDouble(),lng!!.toDouble(), latitude.toDouble() , longitude.toDouble())

                        val hasilHaversine = (havLoga * 1000).toInt()

                        jarak = hasilHaversine.toString() + " m"

                        inserta(ambilData.get(position)?.photos?.get(0)?.photoReference.toString(),
                            ambilData.get(position)?.name.toString(),
                            ambilData.get(position)?.geometry?.location?.lat.toString(),
                            ambilData.get(position)?.geometry?.location?.lng.toString(),
                            ambilData.get(position)?.rating.toString(),
                            jarak)
                        val intent = Intent(context, CartActivity::class.java)
                        context.startActivity(intent)

                    }
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    123
                )
            }


        }


    }

    private fun inserta(gambar: String, name: String, latitude: String, longitude: String, rate: String, jarak: String) {
        Completable.fromAction { val data = CartLocation(gambar,name, latitude,longitude,rate, jarak)
            userDatabase.cartLocationDatabaseDAO.insert(data)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    toast("Data store successfully")
                    notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {
                    toast(e.message.toString())
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
