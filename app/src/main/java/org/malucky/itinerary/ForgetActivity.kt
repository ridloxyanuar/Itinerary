package org.malucky.itinerary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget.*
import org.malucky.itinerary.BaseActivity
import org.malucky.itinerary.R
import org.malucky.itinerary.SignInActivity

class ForgetActivity : BaseActivity() {

    private var mFirebaseAuth: FirebaseAuth? = null

    override fun getView(): Int = R.layout.activity_forget

    override fun onActivityCreated() {
        mFirebaseAuth = FirebaseAuth.getInstance()

        txt_kembali_forget.setOnClickListener{ loginIntent() }

        btn_resetPassword.setOnClickListener {
            val et_reset = editText_reset.text.toString()
            if (TextUtils.isEmpty(et_reset)){
                Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else{
                mFirebaseAuth!!.sendPasswordResetEmail(et_reset).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(
                            this@ForgetActivity,
                            "Check email to reset your password!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            this@ForgetActivity,
                            "Fail to send reset password email!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun loginIntent() {
        startActivity(Intent(this@ForgetActivity, SignInActivity::class.java))
        finish()
    }
}