package org.malucky.itinerary

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import com.github.vipulasri.timelineview.TimelineView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_atur.*
import kotlinx.android.synthetic.main.atur_list_item.*
import org.malucky.itinerary.Presenters.TimeLineAdapter
import org.malucky.itinerary.db.CartLocation
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AturActivity : BaseActivity(), TimeLineAdapter.OnItemClickListner{

    private lateinit var mLayoutManager: LinearLayoutManager

    override fun getView(): Int = R.layout.activity_atur

    override fun onActivityCreated() {
        toolbar_atur.setTitle("Hasil Rekomendasi")
        setSupportActionBar(toolbar_atur)

        toolbar_atur.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_atur.setNavigationOnClickListener {
            finish()
        }


        setupFrogoRecyclerView()
        setupFrogoRecyclerView2()

    }

    private fun setupFrogoRecyclerView2() {
        val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
        val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
        val listDataChartLocation = Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        rv_atur2.apply {
            layoutManager = mLayoutManager
            adapter =  TimeLineAdapter(this@AturActivity, listDataChartLocation, this@AturActivity)
        }

    }

    private fun setupFrogoRecyclerView() {
        val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
        val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
        val listDataChartLocation = Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        rv_atur.apply {
            layoutManager = mLayoutManager
            adapter =  TimeLineAdapter(this@AturActivity, listDataChartLocation, this@AturActivity)
        }

    }

    override fun onItemClick(item: List<CartLocation?>, position: Int) {
        val namaLokasi = item.get(position)!!.namaLokasi
        MaterialDialog(this@AturActivity).show {
                    title(null, "Buat Pengingat Untuk Berkunjung Ke $namaLokasi ?")
                    dateTimePicker(requireFutureDateTime = true) { material, dateTime ->

                    }
                }
        }


}
