package com.example.melodyquest.di

import android.content.Context
import com.example.melodyquest.data.trackplayer.AudioTrackTrackPlayerImp
import com.example.melodyquest.data.trackplayer.SoundPoolTrackPlayerImp
import com.example.melodyquest.domain.trackplayer.TrackPlayerInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun bindTrackPlayer(
        @ApplicationContext context: Context
    ): TrackPlayerInterface {
        return AudioTrackTrackPlayerImp(context)
    }
}

