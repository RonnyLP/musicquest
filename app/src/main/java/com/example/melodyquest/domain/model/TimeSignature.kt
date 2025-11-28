package com.example.melodyquest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TimeSignature(
    val beatsPerMeasure: Int,
    val measures: Int
) {
    companion object {
        val TWO_FOUR = TimeSignature(beatsPerMeasure = 2, measures = 4)
        val THREE_FOUR = TimeSignature(beatsPerMeasure = 3, measures = 4)
        val FOUR_FOUR = TimeSignature(beatsPerMeasure = 4, measures = 4)

        val defaults = listOf(TWO_FOUR, THREE_FOUR, FOUR_FOUR)
    }

    fun toDisplayString(): String {
        return "$beatsPerMeasure/$measures"
    }
}
