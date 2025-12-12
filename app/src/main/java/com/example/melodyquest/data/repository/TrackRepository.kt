package com.example.melodyquest.data.repository

import android.util.Log
import com.example.melodyquest.data.local.dao.TrackDAO
import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.data.remote.TrackRemoteDataSource
import com.example.melodyquest.domain.model.TrackConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.util.UUID
import javax.inject.Inject


class TrackRepository @Inject constructor(
    private val trackDao: TrackDAO,
    private val remote: TrackRemoteDataSource
) {

    suspend fun syncTracks(email: String) {
        val remoteTracks = remote.getTracks(email)

        trackDao.deleteTracksForUser(email)
        trackDao.insertTracks(remoteTracks)
    }

    suspend fun getLocalTracks(email: String): List<Track> =
        trackDao.getTracksForUser(email)

    suspend fun getTrackById(id: String): Track? =
        trackDao.getTrackById(id)

    suspend fun addTrack(email: String, name: String, config: TrackConfiguration) {
        val id = UUID.randomUUID().toString()

        val track = Track(
            id = id,
            name = name,
            data = config,
            ownerEmail = email
        )

        remote.addTrack(email, track)
        trackDao.insertTrack(track)
    }

    suspend fun deleteTrack(email: String, track: Track) {
        remote.deleteTrack(email, track.id)
        trackDao.deleteTrack(track)
    }

    suspend fun updateTrack(email: String, track: Track) {
        remote.updateTrack(email, track)
        trackDao.updateTrack(track)
    }


    fun getTracksFlow(email: String): Flow<List<Track>> =
        trackDao.getTracksFlow(email)
            .onEach { tracks ->
                Log.d("TRACKS_FROM_ROOM", "Total tracks: ${tracks.size}")
                tracks.forEach { track ->
                    Log.d("TRACKS_FROM_ROOM", "Track ID = ${track.id}")
                }
            }

}
