package org.malucky.itinerary.itinerary

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun getViewId(): Int = R.layout.fragment_itinerary

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
        var status = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)

        if (status == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    val lati = location?.latitude
                    val lng = location?.longitude

                    presenter.getData(lati.toString(),lng.toString())

                }
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                123
            )
        }
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
        intent.putExtra("IMAGE", item.get(position)?.photos?.get(0)?.photoReference)
        intent.putExtra("PLACE_ID", item.get(position)?.placeId)
        intent.putExtra("LOCATION_NAME", item.get(position)?.name)
        intent.putExtra("LOCATION_VICINITY", item.get(position)?.vicinity)
        intent.putExtra("LOCATION_RATING", item.get(position)?.rating.toString())
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