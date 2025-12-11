package com.example.melodyquest.feature.home.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.location.LocationListener
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.activity.result.launch
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.melodyquest.data.repository.LocationRepository
import com.example.melodyquest.domain.model.UserLocation
import com.example.melodyquest.service.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class UsuarioTabViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val application: Application
) : ViewModel(), IUsuarioTabViewModel {

    companion object {
        private const val TAG = "UsuarioTabViewModel"
    }

    private val _location = MutableStateFlow<UserLocation?>(null)
    override val location = _location.asStateFlow()

    private val updateInterval = 10_000L

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)



    private var locationCallback: LocationCallback? = null

    override fun startLocationUpdates() {
        if (locationCallback != null) return

        val hasPermission = ActivityCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


        if (!hasPermission) {
            return
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            updateInterval
        ).build()


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val lastLoc = result.lastLocation ?: return

                val loc = UserLocation(lastLoc)
                _location.value = loc

                sendLocationToFirebase(loc)

            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }
    override fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
        locationCallback = null
    }

    override fun onCleared() {
        stopLocationUpdates()
        super.onCleared()
    }


    private fun sendLocationToFirebase(location: UserLocation) {
        val user = auth.currentUser ?: return

        val userRef = database.getReference("user_locations")
            .child(user.uid)
            .child("location")

        val data = mapOf(
            "latitude" to location.latitude,
            "longitude" to location.longitude,
            "timestamp" to System.currentTimeMillis()
        )

        userRef.setValue(data)
    }


    override fun startGoogleAuth() {

    }

}