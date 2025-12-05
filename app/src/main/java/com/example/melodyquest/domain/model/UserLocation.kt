package com.example.melodyquest.domain.model

import android.location.Location

data class UserLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
) {
    constructor(location: Location): this(
        location.latitude,
        location.longitude,
        System.currentTimeMillis()
    )
}
