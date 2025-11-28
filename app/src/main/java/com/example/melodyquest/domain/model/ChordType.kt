package com.example.melodyquest.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ChordType (
    val intervals: List<Int>,
    val chordName: String,
    val getAbbrName: (Note) -> String
)

object ChordTypes {
    val MAJOR = ChordType(listOf(0, 4, 7), "Mayor") {
        note -> note.getDisplayName()
    }
    val MINOR = ChordType(listOf(0, 3, 7), "Menor") {
        note -> note.getDisplayName() + "m"
    }
    val DIMINISHED = ChordType(listOf(0, 3, 6), "Disminuido") {
        note -> note.getDisplayName() + "dim"
    }
    val AUGMENTED = ChordType(listOf(0, 4, 8), "Aumentado") {
        note -> note.getDisplayName() + "+"
    }

    val entries = listOf(MAJOR, MINOR, DIMINISHED, AUGMENTED)
    fun ordinal(ChordType: ChordType): Int {
        return entries.indexOf(ChordType)
    }
}