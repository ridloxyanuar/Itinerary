package org.malucky.itinerary.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_budaya.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_image.*
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.DetailLocationActivity
import org.malucky.itinerary.EditProfilActivity
import org.malucky.itinerary.Presenters.*
import org.malucky.itinerary.R
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.data.Kategori
import org.malucky.itinerary.data.ResultsItem
import org.malucky.itinerary.util.GPSTracker
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment(), NearbyViews ,PopulerAdapter.OnLocationItemClickListner{
    private var mainImageUri: Uri? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var gps: GPSTracker
    lateinit var presenter : NearbyPresenterImp

    override fun getViewId(): Int = R.layout.fragment_home

    companion object {
        fun newInstance(): HomeFragment =
            HomeFragment()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // permission was granted, yay! Do the
                } else {
                    Toast.makeText(activity, "This app requires Access Location", Toast.LENGTH_LONG).show()
//                    finish()
                }
                return
            }
        }    }

    override fun onFragmentCreated() {
        //darkmode
//        val appSettingPrefs: SharedPreferences = activity!!.getSharedPreferences("AppSettingPrefs", 0)
//        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
//        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)
//
//        if(isNightModeOn){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//
//        iv_darkmode.setOnClickListener {
//            if(isNightModeOn){
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                sharedPrefsEdit.putBoolean("NightMode", false)
//                sharedPrefsEdit.apply()
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                sharedPrefsEdit.putBoolean("NightMode", true)
//                sharedPrefsEdit.apply()
//            }
//        }

        main_lay_reload.setOnRefreshListener {
            gps = GPSTracker(activity)

            initPresenter()
            initView(gps)

            main_lay_reload.isRefreshing = false
        }
        //gps
        gps = GPSTracker(activity)

        initPresenter()
        initView(gps)

        txt_editprofile.setOnClickListener {
            startActivity(Intent(activity, EditProfilActivity::class.java))
        }

        //imageSlider
        imageSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!

        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT)
        imageSlider.setIndicatorSelectedColor(Color.WHITE)
        imageSlider.setIndicatorUnselectedColor(Color.GRAY)
        imageSlider.setScrollTimeInSec(5)
        imageSlider.setAutoCycle(true)
        imageSlider.startAutoCycle()


        /////////////////////////////////////////////////////////////////////////////////////////////



        auth = FirebaseAuth.getInstance()
        val user_id = auth.currentUser!!.uid
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("Users").document(user_id).get()
            .addOnSuccessListener(OnSuccessListener<DocumentSnapshot> { documentSnapshot ->
                if (isAdded) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val name = documentSnapshot.getString("name")
                    val image = documentSnapshot.getString("image")
                    assert(user != null)

                    if (name == null && image == null){
                        mainImageUri == null
                        tv_name.setText(getString(R.string.fullname))
                    }else{
                        mainImageUri = Uri.parse(image)
                        tv_name.setText(name)
                    }
                    val placeholderRequest = RequestOptions()
                    placeholderRequest.placeholder(R.drawable.user_male)
                    Glide.with(context!!).setDefaultRequestOptions(placeholderRequest)
                        .load(image).into(iv_user)

                    iv_user.setVisibility(View.VISIBLE)
                    tv_name.setVisibility(View.VISIBLE)
                }
            })



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

    private fun initPresenter() {
        presenter = NearbyPresenterImp(this)
    }

    private fun initView(gps: GPSTracker?) {
//        var result = 0.0
        if (gps!!.canGetLocation()){

            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                123
            )

            val lat = gps.latitude
            val lng = gps.longitude

            presenter.getData(lat.toString(),lng.toString())
//            presenter.getDataPopuler(lat.toString(),lng.toString())

            val geocoder = Geocoder(activity, Locale.getDefault())
            val listAddress : List<Address> = geocoder.getFromLocation(lat, lng, 1)

            if (listAddress.size > 0){
                val address : Address = listAddress.get(0)
                val hasil = address.subAdminArea

                tv_lokasi.text = hasil
            }else{
                tv_lokasi.text = getString(R.string.notfound)
            }

        }else{

            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                123
            )

            gps.showSettingsAlert()
            tv_lokasi.text = getString(R.string.notfound)

        }
    }


    private fun setKategori(): ArrayList<Kategori> {
        var arrayList: ArrayList<Kategori> = ArrayList()

        arrayList.add(Kategori(R.drawable.iconnnn_02, getString(R.string.kategori_terdekat)))
        arrayList.add(Kategori(R.drawable.iconcarrr, getString(R.string.kategori_rental)))
        arrayList.add(Kategori(R.drawable.iconnnn_03, getString(R.string.kategori_kuliner)))
        arrayList.add(Kategori(R.drawable.iconnnn_01, getString(R.string.kategori_budaya)))
        arrayList.add(Kategori(R.drawable.iconspbufix, getString(R.string.kategori_spbu)))
        arrayList.add(Kategori(R.drawable.iconnnn_04, getString(R.string.kategori_jajan)))

        return arrayList
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    override fun Success(datas: List<ResultsItem?>) {
        var adapter = SlideAdapter(datas)
        imageSlider.setSliderAdapter(adapter)

        val sortingPopuler = datas.sortedByDescending { it!!.rating }

        var adapterPopuler = PopulerAdapter(sortingPopuler,this)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_populer.layoutManager = linearLayoutManager
        rv_populer.adapter = adapterPopuler

    }

    override fun Error(pesan: String) {
        Toast.makeText(activity, ""+pesan, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(item: List<ResultsItem?>, position: Int) {
        val intent = Intent(activity, DetailLocationActivity::class.java)
        intent.putExtra("IMAGE", item.get(position)?.photos?.get(0)?.photoReference)
        intent.putExtra("PLACE_ID", item.get(position)?.placeId)
        intent.putExtra("LOCATION_NAME", item.get(position)?.name)
        intent.putExtra("LOCATION_VICINITY", item.get(position)?.vicinity)
        intent.putExtra("LOCATION_RATING", item.get(position)?.rating.toString())
        intent.putExtra("LOCATION_LAT", item.get(position)?.geometry?.location?.lat)
        intent.putExtra("LOCATION_LNG", item.get(position)?.geometry?.location?.lng)
        startActivity(intent)
    }


}