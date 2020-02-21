package org.malucky.itinerary.reusable

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import org.malucky.itinerary.BandungActivity
import org.malucky.itinerary.MainActivity
import org.malucky.itinerary.SignInActivity
import org.malucky.itinerary.SignUpActivity
import javax.inject.Inject

class Navigator @Inject constructor() {

    fun goto(activity: Activity, intent: Intent) {
        try {
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun goForResult(activity: Activity, intent: Intent, requestCode: Int) {
        try {
            activity.startActivityForResult(intent, requestCode)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun mainActivity(activity: Activity) {
        goto(activity, MainActivity.getCallingIntent(activity))
    }

    fun signin(activity: Activity) {
        goto(activity, SignInActivity.getCallingIntent(activity))
    }

    fun signup(activity: Activity) {
        goto(activity, SignUpActivity.getCallingIntent(activity))
    }

    fun bandung(activity: Activity) {
        goto(activity, BandungActivity.getCallingIntent(activity))
    }

}