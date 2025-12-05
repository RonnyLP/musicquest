package com.example.melodyquest.feature.home.ui

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.melodyquest.core.ui.components.GreenButton
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.User
import com.example.melodyquest.feature.home.viewmodel.UsuarioTabViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@Preview
@Composable
fun UsuarioTabPreview() {
    UsuarioTab()
}


@Composable
fun UsuarioTab(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    usuarioTabViewModel: UsuarioTabViewModel = hiltViewModel()
) {


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

        if (granted) {
            usuarioTabViewModel.startLocationUpdates()
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


//    val location by usuarioTabViewModel.location.collectAsStateWithLifecycle()
    val location = usuarioTabViewModel.location.collectAsState()
    val currentUserLocation = location.value

    if (currentUserLocation != null) {
        Text(currentUserLocation.latitude.toString()  + " " + currentUserLocation.longitude.toString())
    }


    // 2. Crear el lanzador de solicitud de permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
            // Permiso concedido, ahora es seguro iniciar las actualizaciones
            Log.d("UsuarioTab", "Permiso de ubicación concedido. Iniciando actualizaciones.")
            usuarioTabViewModel.startLocationUpdates()
        } else {
            // Permiso denegado, informar al usuario (opcional pero recomendado)
            Log.w("UsuarioTab", "Permiso de ubicación denegado.")
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp, 32.dp)
    ) {
        if (currentUserLocation == null) {
            // --- Pantalla de "Iniciar sesión" (sin cambios) ---
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp, 0.dp)
            ) {
                Image(
                    AppIcons.User,
                    contentDescription = "Usuario",
                    modifier = Modifier
                        .size(160.dp)
                )
                Text(
                    text = "Inicia sesión para guardar tus pistas y sincronizarlas en varios dispositivos",
                    textAlign = TextAlign.Center
                )
            }
            GreenButton(
                "Iniciar sesión con Google",
                { usuarioTabViewModel.startGoogleAuth() },
                modifier = Modifier
                    .fillMaxWidth()
            )
        } else {
            LaunchedEffect(Unit) {
                Log.d("UsuarioTab", "Vista de mapa visible. Solicitando permisos.")
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

            // 4. Detener las actualizaciones cuando la pantalla se cierra o el usuario cierra sesión
            // Esto es crucial para ahorrar batería.
            DisposableEffect(Unit) {
                onDispose {
                    Log.d("UsuarioTab", "La pantalla ya no es visible. Deteniendo actualizaciones.")
                    usuarioTabViewModel.stopLocationUpdates()
                }
            }

            val userPosition = LatLng(currentUserLocation.latitude, currentUserLocation.longitude)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(userPosition, 15f)
            }
            val markerState = rememberMarkerState(position = userPosition)

            markerState.position = userPosition

            LaunchedEffect(currentUserLocation) {
                cameraPositionState.animate(
                    com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(userPosition, 15f)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = markerState,
                        title = "Mi Ubicación",
                        snippet = "Aquí estoy"
                    )
                }
            }
        }
    }
}