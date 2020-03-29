package org.malucky.itinerary.Presenters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.malucky.itinerary.R
import org.malucky.itinerary.data.Kategori

class KategoriAdapter(var context: Context, var arrayList: ArrayList<Kategori>) :
    RecyclerView.Adapter<KategoriAdapter.ItemHolder>(){

    class ItemHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var icons = itemView.findViewById<ImageView>(R.id.iv_kategori)
        var titles = itemView.findViewById<TextView>(R.id.txt_kategori)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriAdapter.ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_kategori, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: KategoriAdapter.ItemHolder, position: Int) {
        val kategori: Kategori = arrayList.get(position)

        holder.icons.setImageResource(kategori.icons!!)
        holder.titles.text = kategori.ket

        holder.itemView.setOnClickListener {
            Toast.makeText(context, kategori.ket, Toast.LENGTH_LONG).show()
        }

    }
}