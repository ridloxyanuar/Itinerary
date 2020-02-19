package org.malucky.itinerary.itinerary

import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.R


class ItineraryFragment : BaseFragment() {
    override fun getViewId(): Int = R.layout.fragment_itinerary

//    private val vm by lazy {
//        ViewModelProviders.of(this, injector.dashboardVM()).get(DashboardViewModel::class.java)
//    }

    companion object {
        fun newInstance(): ItineraryFragment =
            ItineraryFragment()
    }


    override fun onFragmentCreated() {

    }


}