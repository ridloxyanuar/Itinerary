package org.malucky.itinerary.itinerary

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.devjurnal.nearbyplacerxkotlinandroid.Presenters.NearbyPresenterImp
import kotlinx.android.synthetic.main.fragment_itinerary.*
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.Presenters.NearbyAdapter
import org.malucky.itinerary.R
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.data.ResultsItem


class ItineraryFragment : BaseFragment(),NearbyViews {
    lateinit var presenter : NearbyPresenterImp


    override fun getViewId(): Int = R.layout.fragment_itinerary

//    private val vm by lazy {
//        ViewModelProviders.of(this, injector.dashboardVM()).get(DashboardViewModel::class.java)
//    }

    companion object {
        fun newInstance(): ItineraryFragment =
            ItineraryFragment()
    }


    override fun onFragmentCreated() {
        initPresenter()
        initView()
    }

    private fun initPresenter() {
        presenter = NearbyPresenterImp(this)
    }

    private fun initView() {
        presenter.getData()
    }

    override fun Success(datas: List<ResultsItem?>) {
        var adapter = NearbyAdapter(datas)
        rv_itinerary.layoutManager = LinearLayoutManager(activity!!)
        rv_itinerary.adapter = adapter
    }

    override fun Error(pesan: String) {
        Toast.makeText(activity!!, ""+pesan, Toast.LENGTH_SHORT).show()
    }


}