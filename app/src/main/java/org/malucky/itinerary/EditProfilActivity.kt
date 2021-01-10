package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_edit_profil.*
import java.util.*

class EditProfilActivity : BaseActivity() {

    private var mainImageUri: Uri? = null
    private var isChanged = false

    override fun getView(): Int = R.layout.activity_edit_profil

    override fun onActivityCreated() {

        auth = FirebaseAuth.getInstance()
        val userId =  auth.currentUser!!.uid

        firebaseFirestore = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        setupToolbar.setTitle(getString(R.string.edit_profil))
        setupToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        setupToolbar.setOnClickListener {
            finish()
        }

        //get data from firebase
        progressBar_setup.visibility = View.VISIBLE
        btn_save_setup.isEnabled = false
        firebaseFirestore.collection("Users").document(userId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result!!.exists()) {
                        val name = task.result!!.getString("name")
                        val image = task.result!!.getString("image")

                        mainImageUri = Uri.parse(image)
                        et_setup_nama.setText(name)

                        val placeholderRequest = RequestOptions()
                        placeholderRequest.placeholder(R.drawable.user_male)
                        Glide.with(this@EditProfilActivity)
                            .setDefaultRequestOptions(placeholderRequest).load(image)
                            .into(iv_avatar_edit)
                    }
                } else {
                    val error = task.exception!!.message
                    Toast.makeText(
                        this@EditProfilActivity,
                        "(FIRESTORE Retrieve Error) : $error",
                        Toast.LENGTH_LONG
                    ).show()
                }
                progressBar_setup.setVisibility(View.INVISIBLE)
                btn_save_setup.setEnabled(true)
            }


        //ambil foto
        btn_takephoto.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this@EditProfilActivity)
        }


        btn_save_setup.setOnClickListener {
            val user_name  = et_setup_nama.getText().toString()

            if (!TextUtils.isEmpty(user_name) && mainImageUri != null) {
                progressBar_setup.setVisibility(View.VISIBLE)
                if (isChanged) {
                    val image_path =
                        storageReference.child("profile_images").child(userId + ".jpg")
                    image_path.putFile(mainImageUri!!).continueWithTask { task ->
                        if (!task.isSuccessful) {
                            throw task.exception!!
                        }
                        image_path.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            storeFireStore(task, user_name)
                        }
                    }
                    //
                } else {
                    storeFireStore(null, user_name)
                }
            }

        }


    }


    //storeFirestore
    fun storeFireStore(task: Task<Uri>?, user_name: String) {
        val user_id = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val downloadUri: Uri?
        downloadUri = if (task != null) {
            task.result
        } else {
            mainImageUri
        }
        val userMap: MutableMap<String, String> =
            HashMap()
        userMap["name"] = user_name
        userMap["image"] = downloadUri.toString()
        firebaseFirestore.collection("Users").document(user_id).set(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@EditProfilActivity, "The user Settings are updated.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val error = task.exception!!.message
                    Toast.makeText(this@EditProfilActivity, "(FIRESTORE Error) : $error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = CropImage.getActivityResult(data)
        if (resultCode == Activity.RESULT_OK) {
            mainImageUri = result.uri
            iv_avatar_edit.setImageURI(mainImageUri)
            isChanged = true
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            val error = result.error
        }
    }
}
