package org.malucky.itinerary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_my_journey.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.malucky.itinerary.data.History
import org.malucky.itinerary.data.Journey
import org.malucky.itinerary.data.Note
import java.util.*

class MyJourneyActivity : BaseActivity() {

    override fun getView(): Int = R.layout.activity_my_journey
    override fun onActivityCreated() {
        auth = FirebaseAuth.getInstance()


        toolbar_my_journey.setTitle(getString(R.string.petualanganmu))
        toolbar_my_journey.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar_my_journey.setNavigationOnClickListener {
            finish()
        }


        //journey
        val queryJourney = FirebaseFirestore.getInstance()
            .collection("Journey")
            .whereEqualTo("userId", auth.uid)
            .orderBy("completed", Query.Direction.ASCENDING)
            .orderBy("created", Query.Direction.DESCENDING)

        queryJourney.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e("EXCEPTION", "Listen failed!", firebaseFirestoreException)
//                return@EventListener
            }

            val journeyList = mutableListOf<Journey>()

            if (querySnapshot != null) {
                for (doc in querySnapshot) {
                    val journey = doc.toObject(Journey::class.java)
                    journeyList.add(journey)
                }
            }

            val userID = FirebaseAuth.getInstance().currentUser!!.uid
            firebaseFirestore = FirebaseFirestore.getInstance()
            
            //adapter
            val adapterCallback = object : FrogoAdapterCallback<Journey> {
                override fun setupInitComponent(view: View, data: Journey) {
                    view.findViewById<TextView>(R.id.txt_namaTempat_journey).text = data.name
                    view.findViewById<TextView>(R.id.txt_tanggal_journey).text = data.created!!.toDate().toLocaleString()
                    view.findViewById<TextView>(R.id.txt_ratingTempat_journey).text = data.rate
                    view.findViewById<TextView>(R.id.txt_jarak_journey).text = data.jarak
                }

                override fun onItemClicked(data: Journey) {
                    val dialog = MaterialDialog(this@MyJourneyActivity).title(null,getString(R.string.petualanganmu))
                        .message(null, "Apakah " + data.name + " Sudah Dikunjungi ?")
                        .negativeButton(null,"Belum") {
                            it.dismiss()
                        }
                        .positiveButton(null,"Sudah",{
                            val history = History(data.name, data.rate,data.jarak , "Sudah Dikunjungi",false, Timestamp(Date()), userID)

                            firebaseFirestore.collection("History")
                                .add(history)
                                .addOnSuccessListener {
                                    finish()
                                    Log.d("OnSucces", "History added successfully")
                                }
                                .addOnFailureListener {
                                    Log.d("OnFailure", it.localizedMessage!!)

                                }
                            it.dismiss()
                        }).noAutoDismiss().cancelable(false)
                    dialog.show()
                }

                override fun onItemLongClicked(data: Journey) {

                }
            }

            rv_my_journey!!.injector<Journey>()
                .addData(journeyList)
                .addCustomView(R.layout.item_list_journey_history)
                .addEmptyView(null)
                .addCallback(adapterCallback)
                .createLayoutLinearVertical(false)
                .createAdapter()
                .build(rv_my_journey)

        }

    }


}
