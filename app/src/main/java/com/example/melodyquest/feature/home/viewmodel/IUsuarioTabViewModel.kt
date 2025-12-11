package com.example.melodyquest.feature.home.viewmodel

import com.example.melodyquest.domain.model.UserLocation
import kotlinx.coroutines.flow.StateFlow

interface IUsuarioTabViewModel {
    val location: StateFlow<UserLocation?>
    fun startLocationUpdates()
    fun stopLocationUpdates()
    fun startGoogleAuth()
}
