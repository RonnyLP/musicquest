package com.example.melodyquest.data.repository

import com.example.melodyquest.data.local.dao.TrackDAO
import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.data.remote.TrackRemoteDataSource
import com.example.melodyquest.domain.model.TrackConfiguration
import kotlinx.coroutines.flow.Flow
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


    fun getTracksFlow(email: String): Flow<List<Track>> =
        trackDao.getTracksFlow(email)

}
