package com.example.melodyquest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ChordConfig(
    val root: Note,
    val octave: Int,
    val type: ChordType,
    val durationBeats: Int
)
