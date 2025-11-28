package com.example.melodyquest.di

import android.app.Application
import com.example.melodyquest.service.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Or another component if more appropriate
object ServiceModule {

    @Provides
    @Singleton // Use @Singleton if you want only one instance of LocationService throughout the app
    fun provideLocationService(application: Application): LocationService {
        return LocationService(application)
    }
}