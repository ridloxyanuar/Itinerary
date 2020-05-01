package org.malucky.itinerary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_atur.*
import org.malucky.itinerary.db.CartLocation


class AturActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atur)

        val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
        val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
        val listDataChartLocation = Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

        for (i in listDataChartLocation) {
            textView5.append(i.namaLokasi + " \n" + i.jarak +" \n\n")

        }

    }
}
