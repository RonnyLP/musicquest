package com.example.melodyquest.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.melodyquest.data.local.entity.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDAO {
    @Query("SELECT * FROM track")
    fun getAllTracks(): Flow<List<Track>>

    @Query("SELECT * FROM Track WHERE ownerEmail = :email")
    suspend fun getTracksForUser(email: String): List<Track>


    @Query("SELECT * FROM track WHERE id = :id")
    fun getTrackById(id: Int): Track

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<Track>)


    @Delete
    suspend fun deleteTrack(track: Track)

    @Update
    suspend fun updateTrack(track: Track)

    @Query("DELETE FROM Track WHERE ownerEmail = :email")
    suspend fun deleteTracksForUser(email: String)

    @Query("SELECT * FROM Track WHERE ownerEmail = :email")
    fun getTracksFlow(email: String): Flow<List<Track>>

}