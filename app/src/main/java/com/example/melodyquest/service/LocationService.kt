package com.example.melodyquest.service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.melodyquest.R
import com.example.melodyquest.data.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LocationService @Inject constructor(
    private val application: Application
): Service() {

    @Inject
    lateinit var locationRepository: LocationRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationCallback: LocationCallback
    // 1. Mueve el FusedLocationProviderClient a una propiedad de la clase
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 2. Decide qué hacer basándose en la acción del Intent
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return START_NOT_STICKY // Recomendado para servicios que pueden ser iniciados y detenidos explícitamente
    }

    // 3. Crea una función `start()` separada para la lógica de inicio
    fun start() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            "location",
            "Location",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("MelodyQuest")
            .setContentText("Enviando ubicación en tiempo real...")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    serviceScope.launch {
                        locationRepository.saveLocation(location.latitude, location.longitude)
                    }
                }
            }
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 10000
        ).build()

        try {
            // 4. Solicita actualizaciones de ubicación aquí
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
        } catch (e: SecurityException) {
            // Maneja la falta de permisos (por ejemplo, deteniendo el servicio)
            stopSelf()
        }

        startForeground(1, notification)
    }

    // 5. Crea una función `stop()` separada para la lógica de detención
    fun stop() {
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        } catch (e: Exception) {
            // En caso de que el callback no estuviera registrado, por ejemplo.
        }
        stopSelf() // Detiene el servicio
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel() // Cancela todas las coroutines cuando el servicio es destruido
    }
}