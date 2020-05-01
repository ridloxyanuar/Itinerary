package org.malucky.itinerary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    private val waktu_loading = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val home = Intent(this@SplashScreenActivity, SignInActivity::class.java)
            startActivity(home)
            finish()
        }, waktu_loading.toLong())
    }
}
