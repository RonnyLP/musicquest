package com.example.melodyquest.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.melodyquest.domain.model.UserLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeUsuarioTabViewModel : ViewModel(), IUsuarioTabViewModel {
    private val _location = MutableStateFlow<UserLocation?>(null)
    override val location = _location.asStateFlow()

    var startLocationUpdatesCalled = false
    var stopLocationUpdatesCalled = false
    var startGoogleAuthCalled = false

    override fun startLocationUpdates() {
        startLocationUpdatesCalled = true
    }

    override fun stopLocationUpdates() {
        stopLocationUpdatesCalled = true
    }

    override fun cerrarSesion(callback: () -> Unit) {
        startGoogleAuthCalled = true
    }

    // Helper function for tests to set a fake location
    fun setFakeLocation(userLocation: UserLocation) {
        _location.value = userLocation
    }
}
