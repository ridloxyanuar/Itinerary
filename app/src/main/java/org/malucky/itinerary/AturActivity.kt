package org.malucky.itinerary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_atur.*

class AturActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atur)

        textView5.text = getIntent().getStringExtra("EXTRA_NAME")
        textView10.text = getIntent().getStringExtra("EXTRA_DISTANCE")
    }
}
