package com.example.melodyquest.domain.model

data class LibraryItem(
    val id: String = "",
    val name: String = "",
    val chords: List<String> = emptyList(),
    val category: String = "" // "popular" o "progression"
)