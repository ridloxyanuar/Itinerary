package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.malucky.itinerary.dashboard.HomeFragment
import org.malucky.itinerary.itinerary.ItineraryFragment
import org.malucky.itinerary.profile.ProfileFragment

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
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        bottom_navigation.setupWithNavController(navController)
    }

    override fun onBackPressed() = when{
        navController.graph.startDestination == navController.currentDestination?.id -> showQuitDialog()
        else -> super.onBackPressed()
    }

    private fun showQuitDialog() {
        val dialog = MaterialDialog(this).title(null,"Quit")
            .message(null, "Are you sure you want to quit?")
            .positiveButton(null,"Yes",{
                it.dismiss()
                finish()
            }).noAutoDismiss().cancelable(false)
            .negativeButton(null,"Cancel",{
                it.dismiss()
            })
        dialog.show()
    }
}


