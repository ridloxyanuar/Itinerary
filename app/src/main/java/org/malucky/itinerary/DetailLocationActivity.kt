package org.malucky.itinerary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import kotlinx.android.synthetic.main.activity_detail_location.*
import kotlinx.android.synthetic.main.activity_terdekat.*
import org.malucky.itinerary.Presenters.DetailsAdapter
import org.malucky.itinerary.Presenters.NearbyPresenterImp
import org.malucky.itinerary.Views.DetailsViews
import org.malucky.itinerary.data.ResultsDetailsItem

class DetailLocationActivity : BaseActivity(), DetailsViews {

    lateinit var presenter : NearbyPresenterImp


    override fun getView() = R.layout.activity_detail_location

    override fun onActivityCreated() {
        toolbar_detail.setTitle(getIntent().getStringExtra("LOCATION_NAME"))
        setSupportActionBar(toolbar_detail)

        toolbar_detail.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar_detail.setNavigationOnClickListener {
            finish()
        }

        val image = intent.getStringExtra("IMAGE")
        val placeId = getIntent().getStringExtra("PLACE_ID")
        txt_judul_detail.text = getIntent().getStringExtra("LOCATION_NAME")
        txt_vicinity_detail.text = getIntent().getStringExtra("LOCATION_VICINITY")
        txt_rating_detail.text = getIntent().getStringExtra("LOCATION_RATING") + " Rating"
//        image_car.setImageResource(getIntent().getStringExtra("CARLOGO").toInt())

        Glide.with(this)
            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+image+"&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .error(R.drawable.noimage)
            .into(imageView3)

        initPresenter()
        val idPlace = intent.getStringExtra("PLACE_ID")
        presenter.getPlaceDetails(idPlace!!)


    }

    private fun initPresenter() {
        presenter = NearbyPresenterImp(this)
    }

    override fun Success(datas: ResultsDetailsItem) {
        var adapter = DetailsAdapter(datas)
        rv_weekday.layoutManager = LinearLayoutManager(applicationContext)
        rv_weekday.adapter = adapter
    }

    override fun Error(pesan: String) {
        Toast.makeText(applicationContext, ""+pesan, Toast.LENGTH_SHORT).show()
    }

}
