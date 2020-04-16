package org.malucky.itinerary.Views

import org.malucky.itinerary.db.CartLocation

interface CartCallback {
    fun onItemClick(listData: List<CartLocation>)
}