package com.example.melodyquest.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.melodyquest.data.local.entity.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDAO {
    @Query("SELECT * FROM track")
    fun getAllTracks(): Flow<List<Track>>

    @Query("SELECT * FROM track WHERE id = :id")
    fun getTrackById(id: Int): Track

    @Insert
    suspend fun insertTrack(track: Track)

    @Delete
    suspend fun deleteTrack(track: Track)

    @Update
    suspend fun updateTrack(track: Track)




}