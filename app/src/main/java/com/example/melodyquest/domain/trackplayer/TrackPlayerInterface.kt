package com.example.melodyquest.domain.trackplayer

import com.example.melodyquest.domain.model.ChordConfig
import com.example.melodyquest.domain.model.ChordType
import com.example.melodyquest.domain.model.Note
import com.example.melodyquest.domain.model.TrackConfiguration
import com.example.melodyquest.domain.model.TrackTimeline
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface TrackPlayerInterface {

    val isReady: StateFlow<Boolean>

    fun setupTrack(trackConfiguration: TrackConfiguration)
    fun playTrack()
    fun pauseTrack()
    fun stopTrack()
    fun release()


}