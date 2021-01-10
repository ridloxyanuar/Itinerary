package org.malucky.itinerary.Presenters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.malucky.itinerary.R
import org.malucky.itinerary.Settings.BahasaActivity
import org.malucky.itinerary.Settings.TemaActivity
import org.malucky.itinerary.data.Kategori
import org.malucky.itinerary.data.Settings
import org.malucky.itinerary.kategori.*
import org.malucky.itinerary.reusable.Navigator

class SettingsAdapter(var context: Context, var arrayList: ArrayList<Settings>) :
    RecyclerView.Adapter<SettingsAdapter.ItemHolder>(){

    class ItemHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var icons = itemView.findViewById<ImageView>(R.id.iv_setting_icons)
        var titles = itemView.findViewById<TextView>(R.id.tv_setting_judul)
        var ket = itemView.findViewById<TextView>(R.id.tv_setting_ket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsAdapter.ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_settings, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: SettingsAdapter.ItemHolder, position: Int) {
        val settings: Settings = arrayList.get(position)

        holder.icons.setImageResource(settings.icons!!)
        holder.titles.text = settings.judul
        holder.ket.text = settings.ket


        holder.itemView.setOnClickListener {
            if (holder.titles.text.equals("Tema") || holder.titles.text.equals("Theme")){
                val temaIntent = Intent(context, TemaActivity::class.java)
                context.startActivity(temaIntent)
            }else if (holder.titles.text.equals("Bahasa") || holder.titles.text.equals("Language")){
                val bahasaIntent = Intent(context, BahasaActivity::class.java)
                context.startActivity(bahasaIntent)
            }
        }

    }
}