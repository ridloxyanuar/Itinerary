package org.malucky.itinerary.profile

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_profile.*
import org.malucky.itinerary.*
import org.malucky.itinerary.data.History
import org.malucky.itinerary.data.Journey
import org.malucky.itinerary.data.Note


class ProfileFragment : BaseFragment() {

    private var mainImageUri: Uri? = null


    override fun getViewId(): Int = R.layout.fragment_profile

    companion object {
        fun newInstance(): ProfileFragment =
            ProfileFragment()
    }


    override fun onFragmentCreated() {

        auth = FirebaseAuth.getInstance()
        val user_id = auth.currentUser!!.uid
        val firebaseFirestore = FirebaseFirestore.getInstance()

        //version
        val pInfo = activity!!.packageManager.getPackageInfo(activity!!.packageName, 0)
        val version = "v"+ pInfo.versionName
//        tv_version.text = version

        iv_setting.setOnClickListener {
            val settingIntent = Intent(activity, SettingsActivity::class.java)
            startActivity(settingIntent)
        }

        txt_signOut.setOnClickListener {
            createDialogSignout()
        }

        tv_ubah_profil.setOnClickListener {
            val intent = Intent(activity, EditProfilActivity::class.java)
            startActivity(intent)
        }

        tv_add_story.setOnClickListener {
            val intent = Intent(activity, AddStoryActivity::class.java)
            startActivity(intent)
        }

        tv_all_journey.setOnClickListener {
            val journeyIntent = Intent(activity, MyJourneyActivity::class.java)
            startActivity(journeyIntent)
        }

        tv_history.setOnClickListener {
            val historyIntent = Intent(activity, MyJourneyActivity::class.java)
            startActivity(historyIntent)
        }

        iv_avatar_profil.setVisibility(View.INVISIBLE)
        tv_nama_profil.setVisibility(View.INVISIBLE)
        tv_email_profile.setVisibility(View.INVISIBLE)


        firebaseFirestore.collection("Users").document(user_id).get()
            .addOnSuccessListener(OnSuccessListener<DocumentSnapshot> { documentSnapshot ->
                if (isAdded) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val name = documentSnapshot.getString("name")
                    val image = documentSnapshot.getString("image")
                    assert(user != null)

                    if (name == null && image == null){
                        mainImageUri == null
                        tv_nama_profil.setText(getString(R.string.fullname))
                        tv_email_profile.text = auth.currentUser?.email
                    }else{
                        mainImageUri = Uri.parse(image)
                        tv_nama_profil.setText(name)
                        tv_email_profile.text = auth.currentUser?.email
                    }
                    val placeholderRequest = RequestOptions()
                    placeholderRequest.placeholder(R.drawable.user_male)
                    Glide.with(context!!).setDefaultRequestOptions(placeholderRequest)
                        .load(image).into(iv_avatar_profil)

                    iv_avatar_profil.setVisibility(View.VISIBLE)
                    tv_nama_profil.setVisibility(View.VISIBLE)
                    tv_email_profile.setVisibility(View.VISIBLE)
                }
            })




        val query = FirebaseFirestore.getInstance()
            .collection("Notes")
            .whereEqualTo("userId", auth.uid)
            .orderBy("completed", Query.Direction.ASCENDING)
            .orderBy("created", Query.Direction.DESCENDING)

        query.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e("EXCEPTION", "Listen failed!", firebaseFirestoreException)
//                return@EventListener
            }

            val notesList = mutableListOf<Note>()

            if (querySnapshot != null) {
                for (doc in querySnapshot) {
                    val note = doc.toObject(Note::class.java)
                    notesList.add(note)
                }
            }
            //adapter
            val adapterCallback = object : FrogoAdapterCallback<Note> {
                override fun setupInitComponent(view: View, data: Note) {
                    val tanggal = data.created
                    view.findViewById<TextView>(R.id.tv_judul_cerita).text = data.gambar
                    view.findViewById<TextView>(R.id.txt_tanggal_cerita).text = tanggal!!.toDate().toLocaleString()
                    view.findViewById<TextView>(R.id.txt_isi_cerita).text = data.name
                }

                override fun onItemClicked(data: Note) {
                    val detailCerita = Intent(activity, DetailCeritaActivity::class.java)
                    detailCerita.putExtra("JUDUL", data.gambar)
                    detailCerita.putExtra("TANGGAL_CERITA", data.created!!.toDate().toLocaleString())
                    detailCerita.putExtra("ISI_CERITA", data.name)
                    startActivity(detailCerita)
                }

                override fun onItemLongClicked(data: Note) {

                }
            }

            rv_diaryTravel!!.injector<Note>()
                .addData(notesList)
                .addCustomView(R.layout.item_list_story)
                .addEmptyView(null)
                .addCallback(adapterCallback)
                .createLayoutLinearHorizontal(false)
                .createAdapter()
                .build(rv_diaryTravel)

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
            //adapter
            val adapterCallback = object : FrogoAdapterCallback<Journey> {
                override fun setupInitComponent(view: View, data: Journey) {
                    view.findViewById<TextView>(R.id.txt_namaTempat_journey).text = data.name
                    view.findViewById<TextView>(R.id.txt_tanggal_journey).text = data.created!!.toDate().toLocaleString()
                    view.findViewById<TextView>(R.id.txt_ratingTempat_journey).text = data.rate
                    view.findViewById<TextView>(R.id.txt_jarak_journey).text = data.jarak
                }

                override fun onItemClicked(data: Journey) {

                }

                override fun onItemLongClicked(data: Journey) {

                }
            }

            rv_ur_journey!!.injector<Journey>()
                .addData(journeyList)
                .addCustomView(R.layout.item_list_journey)
                .addEmptyView(null)
                .addCallback(adapterCallback)
                .createLayoutLinearHorizontal(false)
                .createAdapter()
                .build(rv_ur_journey)

        }


        //History
        val queryHistory = FirebaseFirestore.getInstance()
            .collection("History")
            .whereEqualTo("userId", auth.uid)
            .orderBy("completed", Query.Direction.ASCENDING)
            .orderBy("created", Query.Direction.DESCENDING)

        queryHistory.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e("EXCEPTION", "Listen failed!", firebaseFirestoreException)
//                return@EventListener
            }

            val historyList = mutableListOf<History>()

            if (querySnapshot != null) {
                for (doc in querySnapshot) {
                    val history = doc.toObject(History::class.java)
                    historyList.add(history)
                }
            }
            //adapter
            val adapterCallback = object : FrogoAdapterCallback<History> {
                override fun setupInitComponent(view: View, data: History) {
                    view.findViewById<TextView>(R.id.txt_namaTempat_history).text = data.name
                    view.findViewById<TextView>(R.id.txt_status_history).text = data.status
                    view.findViewById<TextView>(R.id.txt_ratingTempat_history).text = data.rate
                    view.findViewById<TextView>(R.id.txt_jarak_history).text = data.jarak
                }

                override fun onItemClicked(data: History) {

                }

                override fun onItemLongClicked(data: History) {

                }
            }

            rv_history!!.injector<History>()
                .addData(historyList)
                .addCustomView(R.layout.item_list_history)
                .addEmptyView(null)
                .addCallback(adapterCallback)
                .createLayoutLinearHorizontal(false)
                .createAdapter()
                .build(rv_history)

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