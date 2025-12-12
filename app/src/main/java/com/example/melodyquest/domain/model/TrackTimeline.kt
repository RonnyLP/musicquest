package com.example.melodyquest.domain.model


class TrackTimeline(
    progressionConfig: List<ChordConfig>,
    bpm: Int
) {
    var chordEvents: MutableList<ChordEvent> = mutableListOf()
    val beatDurationMs: Long = 60000L / bpm
    var totalDurationMs: Long
    var totalBeats: Int = 0

    init {
//        progressionConfig.for
        var accumulatedTimeBeats = 0
        for (chordConfig in progressionConfig) {
            chordEvents.add(
                ChordEvent(
                    root = chordConfig.root,
                    octave = chordConfig.octave,
                    typeIdx = chordConfig.typeIdx,
                    startTimeMs = accumulatedTimeBeats * beatDurationMs,
                    durationMs = chordConfig.durationBeats * beatDurationMs
                )
            )
            accumulatedTimeBeats += chordConfig.durationBeats
        }
        totalDurationMs = accumulatedTimeBeats * beatDurationMs
        totalBeats = accumulatedTimeBeats
    }
}