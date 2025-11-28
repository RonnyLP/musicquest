package com.example.melodyquest.feature.home.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.location.LocationListener
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.activity.result.launch
import androidx.annotation.RequiresPermission
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

    // 1. Cliente de GPS y el Callback
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private lateinit var locationCallback: LocationCallback


    init {
        Log.d(TAG, "DAFDSFGDSGFSGDSFG")
        startLocationUpdates()
        // El bloque init ahora solo se usa para observar los datos que se muestran en el mapa.
//        auth.currentUser?.uid?.let { userId ->
//            locationRepository.observeLocation(userId) { dbLocation ->
//                Log.d(TAG, "Dato recibido de la BD para mostrar en el mapa: $dbLocation")
//                _location.value = dbLocation
//            }
//        } ?: run {
//            Log.w(TAG, "Usuario actual nulo, no se puede observar la ubicación.")
//        }

        // Iniciar la recolección de actualizaciones de ubicación

    }

    // 2. La función que inicia el seguimiento GPS desde el ViewModel
    // La anotación SuppressLint es necesaria porque el permiso se comprueba en la UI.
    private val locationListener = LocationListener { newLocation ->
        Log.d(
            TAG,
            "GPS obtuvo nueva ubicación: ${newLocation.latitude}, ${newLocation.longitude}"
        )

        // Lanza una coroutine dentro del ciclo de vida del ViewModel
        viewModelScope.launch {
            // ¡ACCIÓN! AQUÍ EL VIEWMODEL ENVÍA LOS DATOS
            auth.currentUser?.uid?.let { userId ->
                locationRepository.saveLocation(
                    lat = newLocation.latitude,
                    lon = newLocation.longitude
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        Log.d(TAG, "Iniciando seguimiento GPS desde el ViewModel...")
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000L, 0f, locationListener)
    }



    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun getLocationUpdatesFlow() = callbackFlow {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 10000
        ).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.forEach { location ->
                    Log.d(TAG, "Dato recibido: $location")
                }
                locationResult.lastLocation?.let { location ->
                    Log.d(TAG, "Flow: Nueva ubicación recibida: ${location.latitude}, ${location.longitude}")
                    // Ofrece la nueva ubicación al flow
                    trySend(location).isSuccess
                }
            }
        }

        Log.d(TAG, "Flow: Solicitando actualizaciones de ubicación...")
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        // Cuando el flow se cancela, se detienen las actualizaciones
        awaitClose {
            Log.d(TAG, "Flow: Deteniendo actualizaciones de ubicación.")
            fusedLocationClient.removeLocationUpdates(locationCallback) }
    }


    // 3. La función que detiene el seguimiento GPS
    fun stopLocationUpdates() {
        Log.d(TAG, "Deteniendo el seguimiento GPS del ViewModel.")
        locationManager.removeUpdates(locationListener)
    }

    // Limpieza automática cuando el ViewModel se destruye
    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }

    fun startGoogleAuth() {
        // TODO: Not yet implemented
    }
}