package org.malucky.itinerary



class ProfileFragment : BaseFragment() {
    override fun getViewId(): Int = R.layout.fragment_profile

//    private val vm by lazy {
//        ViewModelProviders.of(this, injector.dashboardVM()).get(DashboardViewModel::class.java)
//    }

    companion object {
        fun newInstance(): ProfileFragment =
            ProfileFragment()
    }


    override fun onFragmentCreated() {

    }


}