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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_profile.*
import org.malucky.itinerary.AddStoryActivity
import org.malucky.itinerary.BaseFragment
import org.malucky.itinerary.EditProfilActivity
import org.malucky.itinerary.R
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
                        tv_nama_profil.setText("nama lengkap")
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
                    view.findViewById<TextView>(R.id.tv_example_item).text = data.name
                }

                override fun onItemClicked(data: Note) {

                }

                override fun onItemLongClicked(data: Note) {

                }
            }

            rv_diaryTravel.injector<Note>()
                .addData(notesList)
                .addCustomView(R.layout.frogo_rv_list_type_2)
                .addEmptyView(null)
                .addCallback(adapterCallback)
                .createLayoutLinearVertical(false)
                .createAdapter()
                .build(rv_diaryTravel)

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