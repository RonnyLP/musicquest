package com.example.melodyquest.di

import com.example.melodyquest.data.local.dao.TrackDAO
import com.example.melodyquest.data.remote.TrackLibraryRemoteDataSource
import com.example.melodyquest.data.remote.TrackRemoteDataSource
import com.example.melodyquest.data.repository.LibraryRepository
import com.example.melodyquest.data.repository.TrackRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDatabaseModule {


    @Provides
    @Singleton
    fun provideTrackRepository(trackDao: TrackDAO, trackRemoteDataSource: TrackRemoteDataSource): TrackRepository {
        return TrackRepository(trackDao, trackRemoteDataSource)
    }


    @Provides
    @Singleton
    fun provideTrackRemoteDataSource(firestore: FirebaseFirestore): TrackRemoteDataSource {
        return TrackRemoteDataSource(firestore)
    }


    @Provides
    @Singleton
    fun provideLibraryRemoteDataSource(firestore: FirebaseFirestore): TrackLibraryRemoteDataSource {
        return TrackLibraryRemoteDataSource(firestore)
    }

    @Provides
    @Singleton
    fun provideLibraryRepository(
        remote: TrackLibraryRemoteDataSource
    ): LibraryRepository {
        return LibraryRepository(remote)
    }
}
