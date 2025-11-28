package com.example.melodyquest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackConfiguration (
    val bpm: Int = 120,
    val progressionConfig: List<ChordConfig> = emptyList(),
    val shouldLoop: Boolean = true,
    val metronomeEnabled: Boolean = true,
    val timeSignature: TimeSignature = TimeSignature(4, 4),
    val metronomeCountIn: Boolean = true
)