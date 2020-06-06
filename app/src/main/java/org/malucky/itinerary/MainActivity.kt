package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
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

        val homeFragment = HomeFragment()
        val itineraryFragment = ItineraryFragment()
        val profilFragment = ProfileFragment()
        replaceFragment(homeFragment)


        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    replaceFragment(homeFragment)
                    true
                }
                R.id.itineraryFragment -> {
                    replaceFragment(itineraryFragment)
                    true
                }
                R.id.profileFragment -> {
                    replaceFragment(profilFragment)
                    true
                }
                else -> false
            }
        })
//        bottom_navigation.getLayoutParams().behavior = BottomNavigationBehavior()

        bottom_navigation.setSelectedItemId(R.id.homeFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container, fragment)
        fragmentTransaction.commit()
    }


}


