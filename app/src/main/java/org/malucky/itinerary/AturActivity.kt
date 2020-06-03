package org.malucky.itinerary

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_atur.*
import org.malucky.itinerary.Presenters.TimeLineAdapter
import org.malucky.itinerary.db.CartLocation
import kotlin.collections.ArrayList
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import org.malucky.itinerary.Presenters.TimeLineRatingAdapter
import org.malucky.itinerary.util.NotifyWork
import org.malucky.itinerary.util.NotifyWork.Companion.NOTIFICATION_WORK
import java.util.concurrent.TimeUnit


class AturActivity : BaseActivity(), TimeLineAdapter.OnItemClickListner {

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

        button.setOnClickListener {
            //buat jadwal baru
        }

        button2.setOnClickListener {
            //save to my journey
        }
    }

    private fun setupFrogoRecyclerView2() {
        val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
        val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
        val listDataChartLocation =
            Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

        val sortrating = listDataChartLocation.sortedByDescending { it.rate }

        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        rv_atur2.apply {
            layoutManager = mLayoutManager
            adapter = TimeLineRatingAdapter(this@AturActivity, sortrating, this@AturActivity)
        }

    }

    private fun setupFrogoRecyclerView() {
        val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
        val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
        val listDataChartLocation = Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

        val sortjarak = listDataChartLocation.sortedBy { it.jarak }

        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        rv_atur.apply {
            layoutManager = mLayoutManager
            adapter = TimeLineAdapter(this@AturActivity, sortjarak, this@AturActivity)
        }

    }



    override fun onItemClick(item: List<CartLocation?>, position: Int) {
        val namaLokasi = item.get(position)!!.namaLokasi

        }

    private fun scheduleNotification(data: Data, delay: Long) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(this)
        instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, ExistingWorkPolicy.REPLACE, notificationWork).enqueue()
    }


}
