package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bandung.*
import org.malucky.itinerary.Presenters.NearbyAdapter
import org.malucky.itinerary.Presenters.NearbyPresenterImp
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.data.ResultsItem

class BandungActivity : BaseActivity(), NearbyViews{

    companion object {
        @JvmStatic
        fun getCallingIntent(activity: Activity): Intent {
            return Intent(activity, BandungActivity::class.java)
        }
    }

    lateinit var presenter : NearbyPresenterImp

    override fun getView(): Int = R.layout.activity_bandung


    override fun onActivityCreated() {
        initPresenter()
        initView()
    }

    private fun initView() {
        presenter.getData()
    }

    private fun initPresenter() {
        presenter = NearbyPresenterImp(this)
    }

    override fun Success(datas: List<ResultsItem?>) {
        var adapter = NearbyAdapter(datas)
        rv_bandung.layoutManager = LinearLayoutManager(applicationContext)
        rv_bandung.adapter = adapter
    }

    override fun Error(pesan: String) {
        Toast.makeText(applicationContext, ""+pesan, Toast.LENGTH_SHORT).show()
    }
}
