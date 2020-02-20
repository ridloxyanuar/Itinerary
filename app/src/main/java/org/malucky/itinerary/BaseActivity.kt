package org.malucky.itinerary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.malucky.itinerary.reusable.Navigator

abstract class BaseActivity: AppCompatActivity() {

    var navigate: Navigator = Navigator()

    abstract fun getView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())
        onActivityCreated()
    }

    abstract fun onActivityCreated()
}