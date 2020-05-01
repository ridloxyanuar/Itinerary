package org.malucky.itinerary

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import org.malucky.itinerary.Presenters.NearbyPresenterImp
import org.malucky.itinerary.Views.LastLocation
import kotlin.math.ln

class MainActivity: BaseActivity(){

    companion object {
        @JvmStatic
        fun getCallingIntent(activity: Activity): Intent {
            return Intent(activity, MainActivity::class.java)
        }

    }



    override fun getView(): Int = R.layout.activity_main
    lateinit var navController: NavController
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onActivityCreated() {
        hideKeyboard()
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        bottom_navigation.setupWithNavController(navController)
        var status = ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (status == PackageManager.PERMISSION_GRANTED) {
            currentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                123
            )
        }
    }

    private fun currentLocation() {
//         GET MY CURRENT LOCATION
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                val lati = location?.latitude
                val lng = location?.longitude

            }

    }


    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }




}


