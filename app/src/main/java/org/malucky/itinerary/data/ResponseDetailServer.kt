package org.malucky.itinerary.data

import com.google.gson.annotations.SerializedName

data class ResponseDetailServer(

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<Any?>? = null,

	@field:SerializedName("result")
	val results: ResultsDetailsItem? = null,

	@field:SerializedName("status")
	val status: String? = null
)