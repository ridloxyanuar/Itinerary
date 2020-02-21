package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun getCallingIntent(activity: Activity): Intent {
            return Intent(activity, SignInActivity::class.java)
        }
    }

    override fun getView(): Int = R.layout.activity_sign_in


    override fun onActivityCreated() {
        txt_btnDaftar.setOnClickListener {
            navigate.signup(this)
        }
        btn_masuk.setOnClickListener {
            navigate.mainActivity(this)
        }

    }


}
