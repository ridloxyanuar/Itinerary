package org.malucky.itinerary.Presenters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.place_list_item.view.*
import org.malucky.itinerary.R
import org.malucky.itinerary.data.PhotosItem
import org.malucky.itinerary.data.ResultsItem


class NearbyAdapter(data: List<ResultsItem?>) : RecyclerView.Adapter<NearbyAdapter.MyHolder>() {
     var ambilData : List<ResultsItem?>
     lateinit var context: Context
    init {
        this.ambilData = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.place_list_item, parent,false)
        context = parent.context
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return ambilData.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(ambilData, position)
        val poto = holder.itemView.iv_tempat


        Glide.with(context)
            .load(ambilData.get(position)?.icon)
            .into(poto)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ambilData: List<ResultsItem?>, position: Int) {
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
            if (itemView.txt_open_hours.text.equals("null")){
                itemView.txt_open_hours.text = "Tutup"
                itemView.txt_open_hours.setTextColor(Color.parseColor("#F44336"))
            }else{
                itemView.txt_open_hours.text = "Buka"
                itemView.txt_open_hours.setTextColor(Color.parseColor("#4CAF50"))

            }


//            val requestOptions : RequestOptions;
//


            itemView.setOnClickListener {
                ambilData.get(adapterPosition)
            }
        }

    }


}
