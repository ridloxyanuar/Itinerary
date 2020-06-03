package org.d3ifcool.resepedia

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
import org.malucky.itinerary.R
import org.malucky.itinerary.SignInActivity

class ForgetActivity : AppCompatActivity() {

    private var mFirebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget)


        mFirebaseAuth = FirebaseAuth.getInstance()
        btn_resetPassword.setOnClickListener(View.OnClickListener {
            val emailReset = editText_reset.getText().toString()
            if (!TextUtils.isEmpty(emailReset)) {
                mFirebaseAuth!!.sendPasswordResetEmail(emailReset)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgetActivity,
                                "Check email to reset your password!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@ForgetActivity,
                                "Fail to send reset password email!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
        })

        txt_kembali_forget.setOnClickListener{ loginIntent() }
    }

    private fun loginIntent() {
        startActivity(Intent(this@ForgetActivity, SignInActivity::class.java))
        finish()
    }
}