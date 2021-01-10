package org.malucky.itinerary.Presenters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import org.malucky.itinerary.R
import org.malucky.itinerary.data.ResultsItem
import java.util.*

class SlideAdapter(data: List<ResultsItem?>) : SliderViewAdapter<SlideAdapter.SliderAdapterVH>() {

    var ambilData : List<ResultsItem?>
    lateinit var mContext : Context
    init {
        this.ambilData = data
    }
    class SliderAdapterVH(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {

        var imageViewBackground = itemView!!.findViewById<ImageView>(R.id.iv_auto_image_slider)
        var imageGifContainer = itemView!!.findViewById<ImageView>(R.id.iv_gif_container)
        var textViewDescription = itemView!!.findViewById<TextView>(R.id.tv_auto_image_slider)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.image_slider_layout_item, parent, false)
        return SliderAdapterVH(inflate)
    }

    override fun getCount(): Int {
        if (ambilData.isEmpty()){
            return 0
        }else if(ambilData.size < 5){
            return ambilData.size
        }
        else{
            return 5
        }

        return 5
//        return 0
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        val sliderItem = ambilData.get(position)

        viewHolder!!.textViewDescription.setText(sliderItem!!.name)
        viewHolder!!.textViewDescription.setTextSize(16f)
        viewHolder!!.textViewDescription.setTextColor(Color.WHITE)

        val photoRef = sliderItem.photos?.get(0)?.photoReference


        Glide.with(viewHolder.itemView)
            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photoRef+"&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .fitCenter()
            .error(R.drawable.noimage)
            .into(viewHolder.imageViewBackground)

    }
}