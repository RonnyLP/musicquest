package com.example.melodyquest.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melodyquest.data.repository.SavedAccountsRepository
import com.example.melodyquest.domain.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


interface ISelectAccountViewModel {
    val savedAccounts: StateFlow<List<String>>
    val isLoading: StateFlow<Boolean>

    fun refreshAccounts()
    fun removeAccount(email: String)
    fun addAccount(email: String)
}


//@HiltViewModel
//class SelectAccountViewModel @Inject constructor(
//    private val authRepository: AuthRepository
//) : ViewModel(), ISelectAccountViewModel {
//
//    private val _savedAccounts = MutableStateFlow<List<String>>(emptyList())
//    override val savedAccounts: StateFlow<List<String>> = _savedAccounts.asStateFlow()
//
//    private val _isLoading = MutableStateFlow(false)
//    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
//
//    init {
//        loadSavedAccounts()
//    }
//
//    private fun loadSavedAccounts() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val currentUser = authRepository.getCurrentUser()
//                if (currentUser?.email != null) {
//                    _savedAccounts.value = listOf(currentUser.email)
//                } else {
//                    _savedAccounts.value = emptyList()
//                }
//            } catch (e: Exception) {
//                _savedAccounts.value = emptyList()
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    override fun refreshAccounts() {
//        loadSavedAccounts()
//    }
//
//    override fun removeAccount(email: String) {
//        viewModelScope.launch {
//            try {
//                _savedAccounts.value = _savedAccounts.value.filter { it != email }
//            } catch (e: Exception) {
//                // Manejar error
//            }
//        }
//    }
//
//    override fun addAccount(email: String) {
//        viewModelScope.launch {
//            try {
//                if (!_savedAccounts.value.contains(email)) {
//                    _savedAccounts.value = _savedAccounts.value + email
//                }
//            } catch (e: Exception) {
//                // Manejar error
//            }
//        }
//    }
//}

@HiltViewModel
class SelectAccountViewModel @Inject constructor(
//    private val authRepository: AuthRepository,
    private val savedAccountsRepository: SavedAccountsRepository
) : ViewModel() {

    private val _savedAccounts = MutableStateFlow<List<String>>(emptyList())
     val savedAccounts: StateFlow<List<String>> = _savedAccounts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
     val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadSavedAccounts()
    }

    private fun loadSavedAccounts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _savedAccounts.value = savedAccountsRepository.getSavedAccounts()

            } catch (e: Exception) {
                _savedAccounts.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshAccounts() {
        loadSavedAccounts()
    }

    fun removeAccount(email: String) {
        viewModelScope.launch {
            try {
                _savedAccounts.value = _savedAccounts.value.filter { it != email }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun addAccount(email: String) {
        viewModelScope.launch {
            try {
                if (!_savedAccounts.value.contains(email)) {
                    _savedAccounts.value = _savedAccounts.value + email
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}