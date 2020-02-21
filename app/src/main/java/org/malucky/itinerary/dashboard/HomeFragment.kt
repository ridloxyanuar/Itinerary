package org.malucky.itinerary.dashboard

import kotlinx.android.synthetic.main.fragment_home.*
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.R


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
        btn_bdg.setOnClickListener{
            navigate.bandung(activity!!)
        }
    }


}