package org.malucky.itinerary

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*
import org.malucky.itinerary.data.Location

class MainActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun getCallingIntent(activity: Activity): Intent {
            return Intent(activity, MainActivity::class.java)
        }
    }

    override fun getView(): Int = R.layout.activity_main
    lateinit var navController: NavController

    override fun onActivityCreated() {
        hideKeyboard()
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        bottom_navigation.setupWithNavController(navController)
        var status = ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION)


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
        // GET MY CURRENT LOCATION
//        val mFusedLocation = Location.getFusedLocationProviderClient(this)
//        mFusedLocation.lastLocation.addOnSuccessListener(this, object :
//            OnSuccessListener<Location> {
//            override fun onSuccess(location: Location?) {
//                // Do it all with location
//                Log.d("My Current location", "Lat : ${location?.latitude} Long : ${location?.longitude}")
//                // Display in Toast
//                Toast.makeText(this@MapsActivity,
//                    "Lat : ${location?.latitude} Long : ${location?.longitude}",
//                    Toast.LENGTH_LONG).show()
//            }
//
//        })
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

}
