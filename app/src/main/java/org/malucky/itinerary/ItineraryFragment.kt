package org.malucky.itinerary



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