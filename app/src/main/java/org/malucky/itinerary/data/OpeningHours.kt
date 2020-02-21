package org.malucky.itinerary.data

import com.google.gson.annotations.SerializedName

data class OpeningHours(

	@field:SerializedName("open_now")
	val openNow: Boolean? = null,

	@field:SerializedName("weekday_text")
	val weekdayText: List<Any?>? = null
)