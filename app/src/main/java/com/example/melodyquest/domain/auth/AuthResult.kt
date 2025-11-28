package com.example.melodyquest.domain.auth

data class AuthResult(
    val success: Boolean,
    val user: User? = null,
    val errorMessage: String? = null
)