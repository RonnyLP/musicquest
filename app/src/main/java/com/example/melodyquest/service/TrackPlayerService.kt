package com.example.melodyquest.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
//import androidx.privacysandbox.tools.core.generator.build
import com.example.melodyquest.domain.trackplayer.TrackPlayerInterface
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.melodyquest.R

@AndroidEntryPoint
class TrackPlayerService : Service() {

    @Inject
    lateinit var trackPlayer: TrackPlayerInterface

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                // --- INICIO DE LA CORRECCIÓN ---
                // 1. Crear la notificación.
                val notification = createNotification()
                // 2. Llamar a startForeground() INMEDIATAMENTE.
                //    Usa un ID que no sea 0.
                startForeground(NOTIFICATION_ID, notification)
                // --- FIN DE LA CORRECCIÓN ---

                // Ahora sí, puedes realizar la tarea de larga duración.
                trackPlayer.playTrack()
            }
            ACTION_PAUSE -> {
                trackPlayer.pauseTrack()
                // TODO: Aquí deberías actualizar la notificación para mostrar un estado de "pausa".
                // stopForeground(false) significa que la notificación sigue visible pero el servicio ya no se considera "foreground".
                // Esto es útil si quieres que la notificación se pueda descartar.
                stopForeground(false)
            }
            ACTION_STOP -> {
                trackPlayer.stopTrack()
                stopForeground(true) // true elimina la notificación.
                stopSelf() // Detiene el servicio completamente.
            }
        }
        return START_NOT_STICKY
    }

    private fun createNotification(): Notification {
        // Es obligatorio crear un canal de notificación para Android 8 (API 26) o superior.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Track Player Channel",
                NotificationManager.IMPORTANCE_LOW // Usa LOW para que no haga sonido
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // Construir la notificación
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("MelodyQuest")
            .setContentText("Reproduciendo música...")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // ¡IMPORTANTE! Debes tener un ícono en res/drawable
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    companion object {
        const val ACTION_PLAY = "com.example.melodyquest.service.action.PLAY"
        const val ACTION_PAUSE = "com.example.melodyquest.service.action.PAUSE"
        const val ACTION_STOP = "com.example.melodyquest.service.action.STOP"
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_ID = "track_player_channel"
    }
}
