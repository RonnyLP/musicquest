package com.example.melodyquest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.melodyquest.data.local.dao.SavedAccountDao
import com.example.melodyquest.data.local.dao.TrackDAO
import com.example.melodyquest.data.local.entity.SavedAccount
import com.example.melodyquest.data.local.entity.Track

@Database(
    entities = [Track::class, SavedAccount::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun trackDao(): TrackDAO
    abstract fun  savedAccountDao(): SavedAccountDao

}