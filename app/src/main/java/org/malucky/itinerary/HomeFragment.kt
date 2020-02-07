package org.malucky.itinerary



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

    }


}