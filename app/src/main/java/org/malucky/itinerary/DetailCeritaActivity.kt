package org.malucky.itinerary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_detail_cerita.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.malucky.itinerary.data.Journey

class DetailCeritaActivity : BaseActivity() {

    override fun getView(): Int = R.layout.activity_detail_cerita

    override fun onActivityCreated() {

        toolbar_detail_cerita.setTitle("Detail Cerita")
        toolbar_detail_cerita.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar_detail_cerita.setNavigationOnClickListener {
            finish()
        }

        val judul = intent.getStringExtra("JUDUL")
        val tanggal = intent.getStringExtra("TANGGAL_CERITA")
        val isi = intent.getStringExtra("ISI_CERITA")


        tv_judulCerita_detail.setText(judul)
        tv_tanggalCerita_detail.setText(tanggal)
        tv_isiCerita_detail.setText(isi)

    }

}
