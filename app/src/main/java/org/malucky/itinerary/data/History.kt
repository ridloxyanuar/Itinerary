package org.malucky.itinerary.data

import com.google.firebase.Timestamp

class History {
    var name: String? = null
    var rate: String? = null
    var jarak: String? = null
    var status: String? = null
    var completed: Boolean = false
    var created: Timestamp? = null
    var userId: String? = null

    constructor()

    constructor(
        name: String?,
        rate: String?,
        jarak: String?,
        status: String?,
        completed: Boolean,
        created: Timestamp?,
        userId: String?
    ) {
        this.name = name
        this.rate = rate
        this.jarak = jarak
        this.status = status
        this.completed = completed
        this.created = created
        this.userId = userId
    }


}