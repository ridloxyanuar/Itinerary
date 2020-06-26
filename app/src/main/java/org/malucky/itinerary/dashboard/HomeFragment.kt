package org.malucky.itinerary.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.view.View
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_image.*
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.EditProfilActivity
import org.malucky.itinerary.Presenters.KategoriAdapter
import org.malucky.itinerary.Presenters.NearbyPresenterImp
import org.malucky.itinerary.Presenters.SlideAdapter
import org.malucky.itinerary.R
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.data.Kategori
import org.malucky.itinerary.data.ResultsItem
import org.malucky.itinerary.util.GPSTracker
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment(), NearbyViews {
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
                        tv_name.setText("nama lengkap")
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
            val lat = gps.latitude
            val lng = gps.longitude

            presenter.getData(lat.toString(),lng.toString())

            val geocoder = Geocoder(activity, Locale.getDefault())
            val listAddress : List<Address> = geocoder.getFromLocation(lat, lng, 1)

            if (listAddress.size > 0){
                val address : Address = listAddress.get(0)
                val hasil = address.subAdminArea

                tv_lokasi.text = hasil
            }else{
                tv_lokasi.text = "Lokasi Tidak ditemukan"
            }

        }else{
            gps.showSettingsAlert()
            tv_lokasi.text = "Lokasi Tidak ditemukan"

        }
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

    override fun Success(datas: List<ResultsItem?>) {
        var adapter = SlideAdapter(datas)
        imageSlider.setSliderAdapter(adapter)

    }

    override fun Error(pesan: String) {
        Toast.makeText(activity, ""+pesan, Toast.LENGTH_SHORT).show()
    }


}