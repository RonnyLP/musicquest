package com.example.melodyquest.data.repository

import com.example.melodyquest.data.local.dao.TrackDAO
import com.example.melodyquest.data.local.entity.Track
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TrackRepository @Inject constructor(
    private val trackDao: TrackDAO
) {

    // Listar
    fun getAllTracks(): Flow<List<Track>> {
        return trackDao.getAllTracks()
    }

    // Insertar
    suspend fun insertTrack(track: Track) {
        trackDao.insertTrack(track)
    }

    // Actualizar
    suspend fun updateTrack(track: Track) {
        trackDao.updateTrack(track)
    }

    // Eliminar
    suspend fun deleteTrack(track: Track) {
        trackDao.deleteTrack(track)
    }



}