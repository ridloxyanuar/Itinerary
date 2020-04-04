package org.malucky.itinerary.db
import com.google.gson.annotations.SerializedName
import org.malucky.itinerary.data.Geometry
import org.malucky.itinerary.data.OpeningHours
import org.malucky.itinerary.data.PhotosItem

data class ResultsItemDB(

    @field:SerializedName("reference")
	val reference: String? = null,

    @field:SerializedName("types")
	val types: List<String?>? = null,

    @field:SerializedName("scope")
	val scope: String? = null,

    @field:SerializedName("icon")
	val icon: String? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("opening_hours")
	val openingHours: OpeningHours? = null,

    @field:SerializedName("rating")
	val rating: Float? = null,

    @field:SerializedName("geometry")
	val geometry: Geometry? = null,

    @field:SerializedName("vicinity")
	val vicinity: String? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("photo_reference")
	val photoReference: String? = null,

    @field:SerializedName("photos")
	val photos: List<PhotosItem?>? = null,

    @field:SerializedName("place_id")
	val placeId: String? = null
)