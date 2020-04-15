package org.malucky.itinerary.itinerary

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_itinerary.*
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.DetailLocationActivity
import org.malucky.itinerary.Presenters.NearbyAdapter
import org.malucky.itinerary.Presenters.NearbyPresenterImp
import org.malucky.itinerary.R
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.data.ResultsItem
import org.malucky.itinerary.db.CartLocation


class ItineraryFragment : BaseFragment(),NearbyViews, NearbyAdapter.OnLocationItemClickListner {
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


//        rv_itinerary.
    }

    private fun initPresenter() {
        presenter = NearbyPresenterImp(this)
    }

    private fun initView() {
        presenter.getData()
    }

    override fun Success(datas: List<ResultsItem?>) {
        var adapter = NearbyAdapter(datas, this)
        rv_itinerary.layoutManager = LinearLayoutManager(activity!!)
        rv_itinerary.adapter = adapter
    }


    override fun Error(pesan: String) {
        Toast.makeText(activity!!, ""+pesan, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(item: List<ResultsItem?>, position: Int) {
        val intent = Intent(activity!!, DetailLocationActivity::class.java)
        intent.putExtra("IMAGE", item.get(position)?.icon)
        intent.putExtra("LOCATION_NAME", item.get(position)?.name)
        intent.putExtra("LOCATION_VICINITY", item.get(position)?.vicinity)
        intent.putExtra("LOCATION_RATING", item.get(position)?.rating)
        intent.putExtra("LOCATION_LAT", item.get(position)?.geometry?.location?.lat)
        intent.putExtra("LOCATION_LNG", item.get(position)?.geometry?.location?.lng)
        startActivity(intent)
    }

    fun successGetData(list: List<CartLocation>) {

        if (list.isEmpty()){
//            layout_peek.visibility = View.GONE
            return
        }else{
//            layout_peek.visibility = View.VISIBLE
        }

//        var totalUang = 0
        var jumlahPesanan = 0

        for (keranjang in list){
//            totalUang = totalUang + keranjang.totalPrice
            jumlahPesanan = listOf(keranjang).size
        }

//        txt_item.text = "$jumlahPesanan items:"
//        txt_total_harga.text = "Rp. $totalUang"


//        layout_peek.setOnClickListener {

        }
    }


//}