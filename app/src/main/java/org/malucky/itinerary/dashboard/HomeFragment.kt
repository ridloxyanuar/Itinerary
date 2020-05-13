package org.malucky.itinerary.dashboard

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
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
import org.malucky.itinerary.Presenters.KategoriAdapter
import org.malucky.itinerary.Presenters.NearbyPresenterImp
import org.malucky.itinerary.Presenters.SlideAdapter
import org.malucky.itinerary.R
import org.malucky.itinerary.Views.NearbyViews
import org.malucky.itinerary.data.Kategori
import org.malucky.itinerary.data.ResultsItem


class HomeFragment : BaseFragment(), NearbyViews {
    private var mainImageUri: Uri? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var presenter : NearbyPresenterImp

    override fun getViewId(): Int = R.layout.fragment_home

    companion object {
        fun newInstance(): HomeFragment =
            HomeFragment()
    }


    override fun onFragmentCreated() {
        //imageSlider

        initPresenter()
        initView()

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

    private fun initView() {
        var status = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                123
            )
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