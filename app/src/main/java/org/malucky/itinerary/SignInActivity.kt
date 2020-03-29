package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {



    companion object {
        @JvmStatic
        fun getCallingIntent(activity: Activity): Intent {
            return Intent(activity, SignInActivity::class.java)
        }
        private const val TAG = "EmailPassword"

    }

    override fun getView(): Int = R.layout.activity_sign_in


    override fun onActivityCreated() {
        txt_btnDaftar.setOnClickListener {
            navigate.signup(this)
        }
        btn_masuk.setOnClickListener {
            signIn(et_email_login.text.toString(), et_pass_login.text.toString())
        }
        hideKeyboard()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

    }
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null){
            navigate.mainActivity(this)
        }
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }

    private fun signIn(email: String, password: String){
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
                    navigate.mainActivity(this)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                    // [START_EXCLUDE]
//                    checkForMultiFactorFailure(task.exception!!)
                    // [END_EXCLUDE]
                }

                // [START_EXCLUDE]
//                if (!task.isSuccessful) {
//                    status.setText(R.string.auth_failed)
//                }
//                hideProgressBar()
                // [END_EXCLUDE]
            }

    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = et_email_login.text.toString()
        if (TextUtils.isEmpty(email)) {
            et_email_login.error = "Required."
            valid = false
        } else {
            et_email_login.error = null
        }

        val password = et_pass_login.text.toString()
        if (TextUtils.isEmpty(password)) {
            et_pass_login.error = "Required."
            valid = false
        } else {
            et_pass_login.error = null
        }

        return valid
    }


}
