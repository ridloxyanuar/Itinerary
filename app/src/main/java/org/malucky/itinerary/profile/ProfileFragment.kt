package org.malucky.itinerary.profile

import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.R
import org.malucky.itinerary.data.Note


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

        setupFrogoRecyclerView()
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

    private fun listData(): MutableList<Note> {
        val listString = mutableListOf<Note>()

        return listString
    }

    private fun setupFrogoRecyclerView() {

        val adapterCallback = object : FrogoAdapterCallback<Note> {
            override fun setupInitComponent(view: View, data: Note) {
                // Init component content item recyclerview
                view.findViewById<TextView>(R.id.tv_example_item).text = data.name
            }

            override fun onItemClicked(data: Note) {
                // setup item clicked on frogo recycler view
//                showToast(data.name)
            }

            override fun onItemLongClicked(data: Note) {
                // setup item long clicked on frogo recycler view
//                showToast(data.name)
            }
        }

        rv_diaryTravel.injector<Note>()
            .addData(listData())
            .addCustomView(R.layout.frogo_rv_list_type_2)
            .addEmptyView(null)
            .addCallback(adapterCallback)
            .createLayoutLinearVertical(false)
            .createAdapter()
            .build(rv_diaryTravel)
    }


}