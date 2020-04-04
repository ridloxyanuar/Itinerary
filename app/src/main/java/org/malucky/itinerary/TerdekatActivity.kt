package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bandung.*
import kotlinx.android.synthetic.main.activity_terdekat.*
import org.malucky.itinerary.Presenters.NearbyAdapter
import org.malucky.itinerary.Presenters.NearbyPresenterImp
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.data.ResultsItem

class TerdekatActivity : BaseActivity(), NearbyViews, NearbyAdapter.OnLocationItemClickListner {

    companion object {
        @JvmStatic
        fun getCallingIntent(activity: Activity): Intent {
            return Intent(activity, TerdekatActivity::class.java)
        }
    }

    lateinit var presenter : NearbyPresenterImp

    override fun getView(): Int = R.layout.activity_terdekat

    override fun onActivityCreated() {
        toolbar_terdekat.setTitle("Terdekat Dengan Kamu")
        setSupportActionBar(toolbar_terdekat)

        toolbar_terdekat.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_terdekat.setNavigationOnClickListener {
            finish()
        }
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
        var adapter = NearbyAdapter(datas,this)
        rv_terdekat.layoutManager = LinearLayoutManager(applicationContext)
        rv_terdekat.adapter = adapter
    }

    override fun Error(pesan: String) {
        Toast.makeText(applicationContext, ""+pesan, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(item: List<ResultsItem?>, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
