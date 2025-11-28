package com.example.melodyquest.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Note(
    val semitone: Int,
    val sharpName: String,
    val flatName: String
) {
    fun getDisplayName(useFlat: Boolean = false): String {
        return if (useFlat) flatName else sharpName
    }
}

object Notes {
    val C = Note(0, "C", "C")
    val Cs = Note(1, "C#", "Db")
    val D = Note(2, "D", "D")
    val Ds = Note(3, "D#", "Eb")
    val E = Note(4, "E", "E")
    val F = Note(5, "F", "F")
    val Fs = Note(6, "F#", "Gb")
    val G = Note(7, "G", "G")
    val Gs = Note(8, "G#", "Ab")
    val A = Note(9, "A", "A")
    val As = Note(10, "A#", "Bb")
    val B = Note(11, "B", "B")

    val allNotes = listOf(C, Cs, D, Ds, E, F, Fs, G, Gs, A, As, B)

    fun fromSemitone(semitone: Int): Note? {
        return allNotes.find { it.semitone == semitone % 12 }
    }
}