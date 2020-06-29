package org.malucky.itinerary.data

import com.google.firebase.Timestamp

class Journey {
    var name: String? = null
    var jarak: String? = null
    var rate: String? = null
    var lat: String? = null
    var lng: String? = null
    var completed: Boolean = false
    var created: Timestamp? = null
    var userId: String? = null



    constructor(
        name: String?,
        jarak: String?,
        rate: String?,
        lat: String?,
        lng: String?,
        completed: Boolean,
        created: Timestamp?,
        userId: String?
    ) {
        this.name = name
        this.jarak = jarak
        this.rate = rate
        this.lat = lat
        this.lng = lng
        this.completed = completed
        this.created = created
        this.userId = userId
    }

    constructor()
}