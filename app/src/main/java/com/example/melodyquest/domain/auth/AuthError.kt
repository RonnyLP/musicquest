package com.example.melodyquest.domain.auth

sealed class AuthError(val message: String) {
    object InvalidEmail : AuthError("El correo electrónico no es válido")
    object WeakPassword : AuthError("La contraseña debe tener al menos 6 caracteres")
    object UserNotFound : AuthError("No existe una cuenta con este correo")
    object WrongPassword : AuthError("Contraseña incorrecta")
    object EmailAlreadyInUse : AuthError("Ya existe una cuenta con este correo")
    object NetworkError : AuthError("Error de conexión. Verifica tu internet")
    object TooManyRequests : AuthError("Demasiados intentos. Intenta más tarde")
    data class Unknown(val error: String) : AuthError(error)
}