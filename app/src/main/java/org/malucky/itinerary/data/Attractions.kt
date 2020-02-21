package org.malucky.itinerary.data

import com.google.gson.annotations.SerializedName

 class Attractions {

    @SerializedName("id")
    var id : String = ""

    @SerializedName("place_id")
    var place_id : String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("vicinity")
    var vicinity: String = ""

    @SerializedName("rating")
    var rating: Double = 0.0
}