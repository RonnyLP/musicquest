package com.example.melodyquest.domain.auth

import com.example.melodyquest.domain.auth.AuthResult
import com.example.melodyquest.domain.auth.User
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    /**
     * Obtiene el usuario actualmente autenticado
     */
    fun getCurrentUser(): User?

    /**
     * Observa cambios en el estado de autenticación
     */
    fun observeAuthState(): Flow<User?>

    /**
     * Inicia sesión con email y contraseña
     */
    suspend fun signInWithEmail(email: String, password: String): AuthResult

    /**
     * Registra un nuevo usuario con email y contraseña
     */
    suspend fun signUpWithEmail(email: String, password: String, displayName: String? = null): AuthResult

    /**
     * Cierra la sesión del usuario actual
     */
    suspend fun signOut(): Result<Unit>

    /**
     * Envía un correo de restablecimiento de contraseña
     */
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>

    /**
     * Actualiza el nombre de usuario
     */
    suspend fun updateDisplayName(displayName: String): Result<Unit>

    /**
     * Envía correo de verificación al usuario
     */
    suspend fun sendEmailVerification(): Result<Unit>

    /**
     * Recarga la información del usuario actual
     */
    suspend fun reloadUser(): Result<Unit>

    /**
     * Elimina la cuenta del usuario actual
     */
    suspend fun deleteAccount(): Result<Unit>
}
