package org.malucky.itinerary.data

import com.google.firebase.Timestamp
import java.util.*

class Note {
     var gambar: String? = null
     var name: String? = null
     var completed: Boolean = false
     var created: Timestamp? = null
     var userId: String? = null


    constructor(){}

    constructor(gambar: String?, name: String?, completed: Boolean, created: Timestamp?, userId: String?) {
        this.gambar = gambar
        this.name = name
        this.completed = completed
        this.created = created
        this.userId = userId
    }


}



