package com.example.melodyquest.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melodyquest.domain.auth.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// ========== PREVIEW AUTH VIEWMODEL ==========
/**
 * ViewModel para previews que simula comportamiento sin Firebase
 */

class PreviewAuthViewModel : ViewModel(), IAuthViewModel {

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    override val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    override val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    override val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    override fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
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
            delay(1000)

            val success = email.contains("test") && password.length >= 6

            if (success) {
                _currentUser.value = User(
                    uid = "preview_user_123",
                    email = email,
                    displayName = email.substringBefore("@"),
                    isEmailVerified = true
                )
                _isAuthenticated.value = true
                onResult(true)
            } else {
                _errorMessage.value = "Usuario o contraseña incorrectos"
                onResult(false)
            }

            _isLoading.value = false
        }
    }

    override fun loginWithPassword(email: String, password: String, onResult: (Boolean) -> Unit) {
        login(email, password, onResult)
    }

    override fun register(
        email: String,
        password: String,
        confirmPassword: String,
        displayName: String?,
        onResult: (Boolean) -> Unit
    ) {
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

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            delay(1500)

            _currentUser.value = User(
                uid = "preview_user_new",
                email = email,
                displayName = displayName ?: email.substringBefore("@"),
                isEmailVerified = false
            )
            _isAuthenticated.value = true
            _successMessage.value = "Cuenta creada exitosamente. Verifica tu correo."
            onResult(true)

            _isLoading.value = false
        }
    }

    override fun sendPasswordReset(email: String, onResult: (Boolean) -> Unit) {
        if (!isValidEmail(email)) {
            _errorMessage.value = "El correo electrónico no es válido"
            onResult(false)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            delay(800)
            _successMessage.value = "Se ha enviado un correo de recuperación a $email"
            onResult(true)
            _isLoading.value = false
        }
    }

    override fun signOut(onComplete: () -> Unit) {
        viewModelScope.launch {
            delay(300)
            _currentUser.value = null
            _isAuthenticated.value = false
            onComplete()
        }
    }

    override fun checkSession() {
        _isAuthenticated.value = false
    }

    override fun resendVerificationEmail(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(800)
            _successMessage.value = "Correo de verificación enviado"
            onResult(true)
            _isLoading.value = false
        }
    }

    override fun updateDisplayName(displayName: String, onResult: (Boolean) -> Unit) {
        if (displayName.length < 2) {
            _errorMessage.value = "El nombre debe tener al menos 2 caracteres"
            onResult(false)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            delay(800)
            _currentUser.value = _currentUser.value?.copy(displayName = displayName)
            _successMessage.value = "Nombre actualizado exitosamente"
            onResult(true)
            _isLoading.value = false
        }
    }

    override fun deleteAccount(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000)
            _currentUser.value = null
            _isAuthenticated.value = false
            onResult(true)
            _isLoading.value = false
        }
    }

    override fun clearError() {
        _errorMessage.value = null
    }

    override fun clearSuccessMessage() {
        _successMessage.value = null
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Métodos auxiliares para configurar previews
    fun setPreviewState(
        isLoading: Boolean = false,
        errorMessage: String? = null,
        isAuthenticated: Boolean = false,
        currentUser: User? = null
    ) {
        _isLoading.value = isLoading
        _errorMessage.value = errorMessage
        _isAuthenticated.value = isAuthenticated
        _currentUser.value = currentUser
    }
}


// ========== PREVIEW SELECT ACCOUNT VIEWMODEL ==========



// ========== EJEMPLOS DE USO EN PREVIEWS ==========

/*
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    MelodiQuestTheme {
        WelcomeScreen(
            onNavigateToSelectAccount = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAccountScreenPreview() {
    val viewModel = PreviewSelectAccountViewModel()

    MelodiQuestTheme {
        SelectAccountScreen(
            viewModel = viewModel,
            onNavigateToPasswordLogin = {},
            onNavigateToFullLogin = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordLoginScreenPreview() {
    val viewModel = PreviewAuthViewModel()

    MelodiQuestTheme {
        PasswordLoginScreen(
            username = "arthur.cd@melodiquest.com",
            viewModel = viewModel,
            onNavigateBack = {},
            onLoginSuccess = {},
            onForgotPassword = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FullLoginScreenPreview() {
    val viewModel = PreviewAuthViewModel()

    MelodiQuestTheme {
        FullLoginScreen(
            viewModel = viewModel,
            onNavigateToRegister = {},
            onLoginSuccess = {},
            onForgotPassword = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val viewModel = PreviewAuthViewModel()

    MelodiQuestTheme {
        RegisterScreen(
            viewModel = viewModel,
            onNavigateToLogin = {},
            onRegisterSuccess = {}
        )
    }
}

// Preview con estado de carga
@Preview(showBackground = true)
@Composable
fun FullLoginScreenLoadingPreview() {
    val viewModel = PreviewAuthViewModel().apply {
        viewModelScope.launch {
            _isLoading.value = true
        }
    }

    MelodiQuestTheme {
        FullLoginScreen(
            viewModel = viewModel,
            onNavigateToRegister = {},
            onLoginSuccess = {},
            onForgotPassword = {}
        )
    }
}

// Preview con error
@Preview(showBackground = true)
@Composable
fun FullLoginScreenErrorPreview() {
    val viewModel = PreviewAuthViewModel().apply {
        viewModelScope.launch {
            _errorMessage.value = "Usuario o contraseña incorrectos"
        }
    }

    MelodiQuestTheme {
        FullLoginScreen(
            viewModel = viewModel,
            onNavigateToRegister = {},
            onLoginSuccess = {},
            onForgotPassword = {}
        )
    }
}

// Preview con cuentas personalizadas
@Preview(showBackground = true)
@Composable
fun SelectAccountScreenCustomPreview() {
    val viewModel = PreviewSelectAccountViewModel().apply {
        setPreviewAccounts(listOf(
            "usuario1@test.com",
            "usuario2@test.com",
            "usuario3@test.com"
        ))
    }

    MelodiQuestTheme {
        SelectAccountScreen(
            viewModel = viewModel,
            onNavigateToPasswordLogin = {},
            onNavigateToFullLogin = {}
        )
    }
}

// Preview vacío (sin cuentas)
@Preview(showBackground = true)
@Composable
fun SelectAccountScreenEmptyPreview() {
    val viewModel = PreviewSelectAccountViewModel().apply {
        setPreviewAccounts(emptyList())
    }

    MelodiQuestTheme {
        SelectAccountScreen(
            viewModel = viewModel,
            onNavigateToPasswordLogin = {},
            onNavigateToFullLogin = {}
        )
    }
}
*/

// ========== FACTORY PARA CREAR VIEWMODELS ==========
