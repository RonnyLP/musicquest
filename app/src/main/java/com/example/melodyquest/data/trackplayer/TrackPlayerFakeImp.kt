package com.example.melodyquest.data.trackplayer

import android.content.Context
import com.example.melodyquest.domain.model.TrackConfiguration
import com.example.melodyquest.domain.trackplayer.TrackPlayerInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackPlayerFakeImp (
    private val context: Context
): TrackPlayerInterface {

    val _isReady = MutableStateFlow(true)
    override val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    override fun setupTrack(trackConfiguration: TrackConfiguration) {

    }

    override fun playTrack() {

    }

    override fun pauseTrack() {

    }

    override fun stopTrack() {

    }

    override fun release() {

    }

}