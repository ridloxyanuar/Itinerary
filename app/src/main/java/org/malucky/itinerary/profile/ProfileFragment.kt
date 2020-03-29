package org.malucky.itinerary.profile

import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.R


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

        auth = FirebaseAuth.getInstance()

        txt_signOut.setOnClickListener {
            createDialogSignout()
        }
    }

    private fun createDialogSignout() {
        val dialog = MaterialDialog(activity!!).title(null,"Sign Out")
            .message(null, "Are you sure you want to sign out?")
            .negativeButton(null,"Cancel") {
                it.dismiss()
            }
            .positiveButton(null,"Yes",{
                signOut()
                it.dismiss()
            }).noAutoDismiss().cancelable(false)
        dialog.show()
    }


}