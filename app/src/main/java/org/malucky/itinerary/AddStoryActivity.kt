package org.malucky.itinerary

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_story.*
import org.malucky.itinerary.data.Note
import java.util.*

class AddStoryActivity : BaseActivity() {

    private val compressedImageFile: Bitmap? = null
    private val postImageUri: Uri? = null

    override fun getView(): Int = R.layout.activity_add_story

    override fun onActivityCreated() {
        toolbar_story.setTitle(getString(R.string.tambah_ceritamu))
        setSupportActionBar(toolbar_story)

        toolbar_story.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_story.setNavigationOnClickListener {
            finish()
        }

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        firebaseFirestore = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        btn_add_note.setOnClickListener {
            val judul = et_judul_add.text.toString()
            val cerita = et_add_note.text.toString()

            val notes = Note(judul, cerita, false,Timestamp(Date()), userID)

            firebaseFirestore.collection("Notes")
                .add(notes)
                .addOnSuccessListener {
                    finish()
                    Log.d("OnSucces", "Note added successfully")
                }
                .addOnFailureListener {
                    Log.d("OnFailure", it.localizedMessage!!)

                }
        }








    }
}
