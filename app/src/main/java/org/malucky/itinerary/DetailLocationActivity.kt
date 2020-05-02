package org.malucky.itinerary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_location.*
import kotlinx.android.synthetic.main.activity_terdekat.*

class DetailLocationActivity : BaseActivity() {

    override fun getView() = R.layout.activity_detail_location

    override fun onActivityCreated() {
        toolbar_detail.setTitle("Detail")
        setSupportActionBar(toolbar_detail)

        toolbar_detail.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_detail.setNavigationOnClickListener {
            finish()
        }

        val image = intent.getStringExtra("IMAGE")
        txt_judul_detail.text = getIntent().getStringExtra("LOCATION_NAME")
        txt_vicinity_detail.text = getIntent().getStringExtra("LOCATION_VICINITY")
        txt_rating_detail.text = getIntent().getStringExtra("LOCATION_RATING") + " Rating"
//        image_car.setImageResource(getIntent().getStringExtra("CARLOGO").toInt())

        Glide.with(this)
            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+image+"&key=AIzaSyBopZTpiQKeyI3lFE9oypdFz_vjnZga7-c")
            .error(R.drawable.noimage)
            .into(imageView3)
    }

}
