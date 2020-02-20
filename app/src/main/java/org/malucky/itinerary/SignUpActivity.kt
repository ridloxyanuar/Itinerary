package org.malucky.itinerary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun getCallingIntent(activity: Activity): Intent {
            return Intent(activity, SignUpActivity::class.java)
        }
    }

    override fun getView(): Int = R.layout.activity_sign_up

    override fun onActivityCreated() {
        txt_btnMasuk.setOnClickListener {
            navigate.signin(this)
        }
    }

}
