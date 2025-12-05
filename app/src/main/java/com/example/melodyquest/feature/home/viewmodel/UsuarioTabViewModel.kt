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
    private val locationRepository: LocationRepository,
    private val auth: FirebaseAuth,
    private val application: Application
) : ViewModel() {

    companion object {
        private const val TAG = "UsuarioTabViewModel"
    }

    private val _location = MutableStateFlow<UserLocation?>(null)
    val location = _location.asStateFlow()

    private val updateInterval = 10_000L

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)



    private var locationCallback: LocationCallback? = null

    fun startLocationUpdates() {
        if (locationCallback != null) return

        val hasPermission = ActivityCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


        if (!hasPermission) {
            return
        }

//        Log.d(TAG, "Iniciando seguimiento GPS desde el ViewModel...")
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000L, 0f, locationListener)
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            updateInterval
        ).build()


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { loc ->
                    _location.value = UserLocation(loc)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }
    fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
        locationCallback = null
    }

    override fun onCleared() {
        stopLocationUpdates()
        super.onCleared()
    }

    fun startGoogleAuth() {

    }

}