package org.malucky.itinerary.Presenters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.place_list_item.view.*
import org.malucky.itinerary.R
import org.malucky.itinerary.data.ResultsItem


class NearbyAdapter(data: List<ResultsItem?>) : RecyclerView.Adapter<NearbyAdapter.MyHolder>() {
     var ambilData : List<ResultsItem?>
    init {
        this.ambilData = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.place_list_item, parent,false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return ambilData.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(ambilData, position)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ambilData: List<ResultsItem?>, position: Int) {
            itemView.txt_namaTempat.text = ambilData.get(position)?.name
            itemView.txt_alamtTempat.text = ambilData.get(position)?.vicinity
            itemView.txt_ratingTempat.text = ambilData.get(position)?.rating.toString()


        }

    }


}