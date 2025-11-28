package com.example.melodyquest.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melodyquest.domain.auth.AuthRepository
import com.example.melodyquest.domain.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject



interface IAuthViewModel {
    val isLoading: StateFlow<Boolean>
    val errorMessage: StateFlow<String?>
    val isAuthenticated: StateFlow<Boolean>
    val successMessage: StateFlow<String?>
    val currentUser: StateFlow<User?>

    fun login(email: String, password: String, onResult: (Boolean) -> Unit)
    fun loginWithPassword(email: String, password: String, onResult: (Boolean) -> Unit)
    fun register(email: String, password: String, confirmPassword: String, displayName: String?, onResult: (Boolean) -> Unit)
    fun sendPasswordReset(email: String, onResult: (Boolean) -> Unit)
    fun signOut(onComplete: () -> Unit = {})
    fun checkSession()
    fun resendVerificationEmail(onResult: (Boolean) -> Unit)
    fun updateDisplayName(displayName: String, onResult: (Boolean) -> Unit)
    fun deleteAccount(onResult: (Boolean) -> Unit)
    fun clearError()
    fun clearSuccessMessage()
}


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(), IAuthViewModel {

    // Estados de UI
    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    override val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    override val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    override val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    // Usuario actual observado desde el repositorio
    override val currentUser: StateFlow<User?> = authRepository.observeAuthState()
        .onEach { user ->
            _isAuthenticated.value = user != null
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = authRepository.getCurrentUser()
        )

    init {
        // Verificar sesión al iniciar
        checkSession()
    }

    /**
     * Login con email y contraseña completos
     */
    override fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        // Validaciones básicas
        if (!isValidEmail(email)) {
            _errorMessage.value = "El correo electrónico no es válido"
            onResult(false)
            return
        }

        if (password.isEmpty()) {
            _errorMessage.value = "La contraseña no puede estar vacía"
            onResult(false)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authRepository.signInWithEmail(email, password)

            _isLoading.value = false

            if (result.success) {
                _isAuthenticated.value = true
                onResult(true)
            } else {
                _errorMessage.value = result.errorMessage
                onResult(false)
            }
        }
    }

    /**
     * Login solo con contraseña (para cuentas guardadas)
     */
    override fun loginWithPassword(email: String, password: String, onResult: (Boolean) -> Unit) {
        // Reutilizamos la misma lógica que login completo
        login(email, password, onResult)
    }

    /**
     * Registro de nuevo usuario
     */
    override fun register(
        email: String,
        password: String,
        confirmPassword: String,
        displayName: String?,
        onResult: (Boolean) -> Unit
    ) {
        // Validaciones
        if (!isValidEmail(email)) {
            _errorMessage.value = "El correo electrónico no es válido"
            onResult(false)
            return
        }

        if (password.length < 6) {
            _errorMessage.value = "La contraseña debe tener al menos 6 caracteres"
            onResult(false)
            return
        }

        if (password != confirmPassword) {
            _errorMessage.value = "Las contraseñas no coinciden"
            onResult(false)
            return
        }

        if (!displayName.isNullOrBlank() && displayName.length < 2) {
            _errorMessage.value = "El nombre debe tener al menos 2 caracteres"
            onResult(false)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authRepository.signUpWithEmail(
                email = email,
                password = password,
                displayName = displayName
            )

            _isLoading.value = false

            if (result.success) {
                _isAuthenticated.value = true
                _successMessage.value = "Cuenta creada exitosamente. Verifica tu correo."
                onResult(true)
            } else {
                _errorMessage.value = result.errorMessage
                onResult(false)
            }
        }
    }

    /**
     * Enviar correo de recuperación de contraseña
     */
    override fun sendPasswordReset(email: String, onResult: (Boolean) -> Unit) {
        if (!isValidEmail(email)) {
            _errorMessage.value = "El correo electrónico no es válido"
            onResult(false)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authRepository.sendPasswordResetEmail(email)

            _isLoading.value = false

            if (result.isSuccess) {
                _successMessage.value = "Se ha enviado un correo de recuperación a $email"
                onResult(true)
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message
                    ?: "Error al enviar correo de recuperación"
                onResult(false)
            }
        }
    }

    /**
     * Cerrar sesión
     */
    override fun signOut(onComplete: () -> Unit) {
        viewModelScope.launch {
            val result = authRepository.signOut()

            if (result.isSuccess) {
                _isAuthenticated.value = false
                onComplete()
            } else {
                _errorMessage.value = "Error al cerrar sesión"
            }
        }
    }

    /**
     * Verificar si hay sesión activa
     */
    override fun checkSession() {
        val user = authRepository.getCurrentUser()
        _isAuthenticated.value = user != null
    }

    /**
     * Reenviar correo de verificación
     */
    override fun resendVerificationEmail(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = authRepository.sendEmailVerification()

            _isLoading.value = false

            if (result.isSuccess) {
                _successMessage.value = "Correo de verificación enviado"
                onResult(true)
            } else {
                _errorMessage.value = "Error al enviar correo de verificación"
                onResult(false)
            }
        }
    }

    /**
     * Actualizar nombre de usuario
     */
    override fun updateDisplayName(displayName: String, onResult: (Boolean) -> Unit) {
        if (displayName.length < 2) {
            _errorMessage.value = "El nombre debe tener al menos 2 caracteres"
            onResult(false)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true

            val result = authRepository.updateDisplayName(displayName)

            _isLoading.value = false

            if (result.isSuccess) {
                // Recargar usuario para reflejar cambios
                authRepository.reloadUser()
                _successMessage.value = "Nombre actualizado exitosamente"
                onResult(true)
            } else {
                _errorMessage.value = "Error al actualizar nombre"
                onResult(false)
            }
        }
    }

    /**
     * Eliminar cuenta
     */
    override fun deleteAccount(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = authRepository.deleteAccount()

            _isLoading.value = false

            if (result.isSuccess) {
                _isAuthenticated.value = false
                onResult(true)
            } else {
                _errorMessage.value = "Error al eliminar cuenta"
                onResult(false)
            }
        }
    }

    /**
     * Limpiar mensaje de error
     */
    override fun clearError() {
        _errorMessage.value = null
    }

    /**
     * Limpiar mensaje de éxito
     */
    override fun clearSuccessMessage() {
        _successMessage.value = null
    }

    /**
     * Validación simple de email
     */
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

