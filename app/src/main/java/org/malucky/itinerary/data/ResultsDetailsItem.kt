package org.malucky.itinerary.data
import com.google.gson.annotations.SerializedName

data class ResultsDetailsItem(

	@field:SerializedName("opening_hours")
	val openingHours: OpeningHours? = null

)