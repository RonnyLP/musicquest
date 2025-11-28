package com.example.melodyquest.data.auth

import com.example.melodyquest.domain.auth.AuthError
import com.example.melodyquest.domain.auth.AuthRepository
import com.example.melodyquest.domain.auth.AuthResult
import com.example.melodyquest.domain.auth.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    /**
     * Convierte FirebaseUser a nuestro modelo User
     */
    private fun FirebaseUser.toUser(): User {
        return User(
            uid = uid,
            email = email,
            displayName = displayName,
            isEmailVerified = isEmailVerified
        )
    }

    /**
     * Maneja errores de Firebase y los convierte a AuthError
     */
    private fun handleFirebaseException(exception: Exception): AuthError {
        return when (exception.message) {
            "The email address is badly formatted." -> AuthError.InvalidEmail
            "The given password is invalid. [ Password should be at least 6 characters ]" -> AuthError.WeakPassword
            "There is no user record corresponding to this identifier. The user may have been deleted." -> AuthError.UserNotFound
            "The password is invalid or the user does not have a password." -> AuthError.WrongPassword
            "The email address is already in use by another account." -> AuthError.EmailAlreadyInUse
            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> AuthError.NetworkError
            "We have blocked all requests from this device due to unusual activity. Try again later." -> AuthError.TooManyRequests
            else -> {
                // Intenta detectar errores por código
                when {
                    exception.message?.contains("INVALID_EMAIL") == true -> AuthError.InvalidEmail
                    exception.message?.contains("WEAK_PASSWORD") == true -> AuthError.WeakPassword
                    exception.message?.contains("EMAIL_NOT_FOUND") == true -> AuthError.UserNotFound
                    exception.message?.contains("INVALID_PASSWORD") == true -> AuthError.WrongPassword
                    exception.message?.contains("EMAIL_EXISTS") == true -> AuthError.EmailAlreadyInUse
                    exception.message?.contains("NETWORK_ERROR") == true -> AuthError.NetworkError
                    exception.message?.contains("TOO_MANY_ATTEMPTS_TRY_LATER") == true -> AuthError.TooManyRequests
                    else -> AuthError.Unknown(exception.message ?: "Error desconocido")
                }
            }
        }
    }

    override fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toUser()
    }

    override fun observeAuthState(): Flow<User?> = flow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            // Este callback se ejecuta en el hilo principal
        }

        firebaseAuth.addAuthStateListener(listener)

        // Emitir estado inicial
        emit(firebaseAuth.currentUser?.toUser())

        // Nota: En producción, considera usar callbackFlow para manejar
        // la cancelación apropiadamente y remover el listener
    }

    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.toUser()

            if (user != null) {
                AuthResult(success = true, user = user)
            } else {
                AuthResult(success = false, errorMessage = "Error al obtener datos del usuario")
            }
        } catch (e: Exception) {
            val error = handleFirebaseException(e)
            AuthResult(success = false, errorMessage = error.message)
        }
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        displayName: String?
    ): AuthResult {
        return try {
            // Crear cuenta
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser == null) {
                return AuthResult(success = false, errorMessage = "Error al crear la cuenta")
            }

            // Actualizar nombre de usuario si se proporcionó
            if (!displayName.isNullOrBlank()) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()

                firebaseUser.updateProfile(profileUpdates).await()
            }

            // Enviar correo de verificación
            firebaseUser.sendEmailVerification().await()

            // Recargar usuario para obtener datos actualizados
            firebaseUser.reload().await()

            val user = firebaseUser.toUser()
            AuthResult(success = true, user = user)

        } catch (e: Exception) {
            val error = handleFirebaseException(e)
            AuthResult(success = false, errorMessage = error.message)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDisplayName(displayName: String): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser
                ?: return Result.failure(Exception("No hay usuario autenticado"))

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build()

            user.updateProfile(profileUpdates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendEmailVerification(): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser
                ?: return Result.failure(Exception("No hay usuario autenticado"))

            user.sendEmailVerification().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun reloadUser(): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser
                ?: return Result.failure(Exception("No hay usuario autenticado"))

            user.reload().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser
                ?: return Result.failure(Exception("No hay usuario autenticado"))

            user.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
