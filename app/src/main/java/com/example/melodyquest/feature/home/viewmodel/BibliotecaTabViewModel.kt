package com.example.melodyquest.feature.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.collections.listOf

class BibliotecaTabViewModel: ViewModel() {

    private val _events = MutableSharedFlow<BibliotecaEvent>()
    val events = _events.asSharedFlow()

    fun navigateToPlayer() {
        _events.tryEmit(BibliotecaEvent.NavigateToPlayer)
    }

    val popularSongs =  listOf(
        "Hotel California - Verso",
        "Canon de Pachenbel",
        "Creep",
    )

    val commonProgressions =  listOf(
        "Hotel California - Verso",
        "Canon de Pachenbel",
        "i - VIb - VIIb - i",
    )

    private val _isCloneDialogOpen = mutableStateOf(false)
    val isCloneDialogOpen: State<Boolean> = _isCloneDialogOpen

    fun openCloneDialog() {
        _isCloneDialogOpen.value = true
    }
    fun closeCloneDialog() {
        _isCloneDialogOpen.value = false
    }



}


sealed class BibliotecaEvent {
    object NavigateToPlayer: BibliotecaEvent()
}