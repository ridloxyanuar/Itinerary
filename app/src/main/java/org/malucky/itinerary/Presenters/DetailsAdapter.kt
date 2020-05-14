package org.malucky.itinerary.Presenters

import android.content.Context
import android.text.style.LocaleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weekday_item.view.*
import org.malucky.itinerary.R
import org.malucky.itinerary.data.ResultsDetailsItem
import java.util.*


class DetailsAdapter(data: ResultsDetailsItem) : RecyclerView.Adapter<DetailsAdapter.MyHolder>() {

    var ambilData : ResultsDetailsItem
     lateinit var context: Context
    init {
        this.ambilData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.weekday_item, parent,false)
        context = parent.context
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(ambilData, position)
    }


    private fun toast(s: String) {
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show()
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ambilData: ResultsDetailsItem, position: Int) {

            val weekDays = ambilData.openingHours?.weekdayText
            if (weekDays == null) {
                itemView.tv_weekday.text = "Waktu Jam Buka Tidak Ditemukan"
                itemView.tv_weekday1.visibility = View.GONE
                itemView.tv_weekday2.visibility = View.GONE
                itemView.tv_weekday3.visibility = View.GONE
                itemView.tv_weekday4.visibility = View.GONE
                itemView.tv_weekday5.visibility = View.GONE
                itemView.tv_weekday6.visibility = View.GONE
                Log.d("this", "jadwal harian tidak ditemukan")
            } else {
                itemView.tv_weekday.text = weekDays.get(0).toString()
                itemView.tv_weekday1.text = weekDays.get(1).toString()
                itemView.tv_weekday2.text = weekDays.get(2).toString()
                itemView.tv_weekday3.text = weekDays.get(3).toString()
                itemView.tv_weekday4.text = weekDays.get(4).toString()
                itemView.tv_weekday5.text = weekDays.get(5).toString()
                itemView.tv_weekday6.text = weekDays.get(6).toString()
            }

        }
    }
}
