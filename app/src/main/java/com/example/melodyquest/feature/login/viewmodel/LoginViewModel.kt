package com.example.melodyquest.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import com.example.melodyquest.domain.auth.User

class LoginViewModel: ViewModel() {

    fun loginAsGuest(onSuccess: () -> Unit) {
        // Lógica para iniciar sesión como invitado
        onSuccess()
    }

    fun loginWithGoogle(onSuccess: () -> Unit) {
        // Lógica para iniciar sesión con Google
        onSuccess()
    }


    fun loginWithPassword(username: String, password: String, callback: (Boolean) -> Unit) {

    }

//    val savedAccounts: StateFlow<List<String>>
//    val isLoading: StateFlow<Boolean>

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {

    }
//    fun loginWithPassword(username: String, password: String, onResult: (Boolean) -> Unit)
    fun register(username: String, password: String, onResult: (Boolean) -> Unit) {

    }


}
