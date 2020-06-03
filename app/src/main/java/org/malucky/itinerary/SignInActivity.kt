package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.d3ifcool.resepedia.ForgetActivity

class SignInActivity : BaseActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient


    companion object {
        @JvmStatic
        fun getCallingIntent(activity: Activity): Intent {
            return Intent(activity, SignInActivity::class.java)
        }
            private const val TAG = "EmailPassword"
            private const val TAG_GOOGLE = "GoogleActivity"
            private const val RC_SIGN_IN = 9001


    }

    override fun getView(): Int = R.layout.activity_sign_in


    override fun onActivityCreated() {
        Glide.with(this)
            .load(R.drawable.logo_treavel)
            .into(imageView5)

        txt_btnDaftar.setOnClickListener {
            navigate.signup(this)
        }
        btn_masuk.setOnClickListener {
            signIn(et_email_login.text.toString(), et_pass_login.text.toString())
        }
        txt_forget_pass.setOnClickListener {
            startActivity(Intent(this, ForgetActivity::class.java))
        }

        hideKeyboard()

        // Initialize Firebase Auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        txt_login_google.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

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

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id!!)
        // [START_EXCLUDE silent]
//        showProgressBar()
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    finish()
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
//                hideProgressBar()
                // [END_EXCLUDE]
            }
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
