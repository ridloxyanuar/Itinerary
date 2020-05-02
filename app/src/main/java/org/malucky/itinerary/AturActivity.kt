package org.malucky.itinerary

import android.content.Intent
import android.net.ParseException
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_atur.*
import org.malucky.itinerary.db.CartLocation
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AturActivity : BaseActivity() {

    override fun getView(): Int = R.layout.activity_atur

    override fun onActivityCreated() {
        toolbar_atur.setTitle("Hasil Rekomendasi")
        setSupportActionBar(toolbar_atur)

        toolbar_atur.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_atur.setNavigationOnClickListener {
            finish()
        }




//        for (i in listDataChartLocation) {
//            textView5.append(i.namaLokasi + " \n" + i.jarak +" \n\n")
//
//        }

        setupFrogoRecyclerView()

    }

    private fun setupFrogoRecyclerView() {
        val adapterCallback = object : FrogoAdapterCallback<CartLocation> {
            override fun setupInitComponent(view: View, data: CartLocation) {
                // Init component content item recyclerview
                val latitude = data.latitude
                val longitude = data.longitude

                view.findViewById<TextView>(R.id.txt_namaTempatAtur).text = data.namaLokasi
                view.findViewById<TextView>(R.id.txt_jarakAtur).text = data.jarak
                view.findViewById<ImageButton>(R.id.imageButton2).setOnClickListener {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=$latitude,$longitude")
                    )
                    startActivity(intent)
                }
            }

            override fun onItemClicked(data: CartLocation) {

                val dialog = MaterialDialog(this@AturActivity).title(null,"Pengingat")
                    .message(null, "Buat Pengingat Untuk Berkunjung Ke " + data.namaLokasi + " ?")
                    .negativeButton(null,"Tidak") {
                        it.dismiss()
                    }
                    .positiveButton(null,"Iya",{
                        val calintent = Intent(Intent.ACTION_INSERT)
                        calintent.type = "vnd.android.cursor.item/event"
                        calintent.putExtra("eventLocation", data.namaLokasi)
                        calintent.putExtra("title", "Liburan - iTraveller");
                        startActivity(calintent)
                        it.dismiss()
                    }).noAutoDismiss().cancelable(false)
                dialog.show()
            }

            override fun onItemLongClicked(data: CartLocation) {

            }
        }

        val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
        val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
        val listDataChartLocation = Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)


        rv_atur.injector<CartLocation>()
            .addData(listDataChartLocation)
            .addCustomView(R.layout.atur_list_item)
            .addEmptyView(null)
            .addCallback(adapterCallback)
            .createLayoutLinearVertical(false)
            .createAdapter()
            .build(rv_atur)
    }

    private fun createDialogSignout() {

    }
}
