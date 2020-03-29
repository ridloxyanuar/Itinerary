package org.malucky.itinerary.dashboard

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.Presenters.KategoriAdapter
import org.malucky.itinerary.R
import org.malucky.itinerary.data.Kategori


class HomeFragment : BaseFragment() {


    override fun getViewId(): Int = R.layout.fragment_home

//    private val vm by lazy {
//        ViewModelProviders.of(this, injector.dashboardVM()).get(DashboardViewModel::class.java)
//    }

    companion object {
        fun newInstance(): HomeFragment =
            HomeFragment()
    }


    override fun onFragmentCreated() {
        hideKeyboard()
//        recyclerView = view?.findViewById(R.id.rv_kategori)
        gridLayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        rv_kategori.layoutManager = gridLayoutManager
        rv_kategori.setHasFixedSize(true)

        categoryItem = ArrayList()
        categoryItem = setKategori()
        kategoriAdapters = KategoriAdapter(context!!, categoryItem!!)
        rv_kategori.adapter = kategoriAdapters

    }



    private fun setKategori(): ArrayList<Kategori> {
        var arrayList: ArrayList<Kategori> = ArrayList()

        arrayList.add(Kategori(R.drawable.iconnnn_02, "Terdekat"))
        arrayList.add(Kategori(R.drawable.iconnnn_05, "Buka & Tutup"))
        arrayList.add(Kategori(R.drawable.iconnnn_03, "Kuliner"))
        arrayList.add(Kategori(R.drawable.iconnnn_01, "Budaya"))
        arrayList.add(Kategori(R.drawable.iconnnn_06, "Pengalaman"))
        arrayList.add(Kategori(R.drawable.iconnnn_04, "Oleh-Oleh"))

        return arrayList
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }




}