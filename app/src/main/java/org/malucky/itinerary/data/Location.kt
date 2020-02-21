package org.malucky.itinerary.data

import com.google.gson.annotations.SerializedName

data class Location(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)