package org.malucky.itinerary.util

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_confirmation_dialog.*
import org.malucky.itinerary.R
import java.lang.Math.*
import java.text.SimpleDateFormat
import java.util.*


fun Context.bottomSheetConfirmationDialog(message: String) {
    BottomSheetDialog(this).apply {

        setContentView(R.layout.bottom_sheet_confirmation_dialog)

        tvMessage.text = message

        show()
    }
}


fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6372.8 // in kilometers

    val λ1 = toRadians(lat1)
    val λ2 = toRadians(lat2)
    val Δλ = toRadians(lat2 - lat1)
    val Δφ = toRadians(lon2 - lon1)
    return 2 * R * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2)))
}

internal fun Calendar.formatDateTime(): String {
    return SimpleDateFormat("kk:mm a, dd MMMM yyyy", Locale.getDefault()).format(this.time)
}