package com.example.melodyquest.di

import android.content.Context
import androidx.room.Room
import com.example.melodyquest.data.local.dao.SavedAccountDao
import com.example.melodyquest.data.local.dao.TrackDAO
import com.example.melodyquest.data.local.db.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext application: Context
    ): LocalDatabase {
        return Room.databaseBuilder(
            application,
            LocalDatabase::class.java,
            "my_database"
        ).build()
    }

    @Provides
    fun provideTrackDao(database: LocalDatabase): TrackDAO {
        return database.trackDao()
    }

    @Provides
    fun provideSavedAccountDao(db: LocalDatabase): SavedAccountDao =
        db.savedAccountDao()

}