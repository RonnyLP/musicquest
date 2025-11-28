package com.example.melodyquest.domain.model

data class ChordEvent(
    val root: Note,
    val octave: Int,
    val type: ChordType,
    val startTimeMs: Long,   // instante absoluto (desde el inicio del timeline)
    val durationMs: Long     // duración del acorde
)