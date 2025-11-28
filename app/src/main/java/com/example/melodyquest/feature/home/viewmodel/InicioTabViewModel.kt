package com.example.melodyquest.feature.home.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.data.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InicioTabViewModel @Inject constructor(
//    private val trackRepository: TrackRepository
) : ViewModel() {

    /* Eventos de navegación */

    private val _events = MutableSharedFlow<InicioEvent>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()

    fun navigateToPlayer() {
        Log.d("ViewModelEvent", "Triggering Navigate to player")
        val emitted = _events.tryEmit(InicioEvent.NavigateToPlayer)
        Log.d("ViewModelEvent", "Event emission success: $emitted") // <<< AÑADE ESTO
        if (!emitted) {
            Log.e("ViewModelEvent", "Failed to emit NavigateToPlayer event. No active collectors or buffer full?")
        }
    }

    val progresionesGuardadas = listOf(
        "Everlong",
        "Canon de Pachenbel",
        "Creep",
        "Misplaced",
    )

//    val _progressionList: StateFlow<List<Track>> = trackRepository.getAllTracks()
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val isAddDialogOpen = mutableStateOf(false)

    fun openAddDialog() {
        isAddDialogOpen.value = true
    }
    fun closeAddDialog() {
        isAddDialogOpen.value = false
    }

    val isEditDialogOpen = mutableStateOf(false)

    fun openEditDialog() {
        isEditDialogOpen.value = true
    }
    fun closeEditDialog() {
        isEditDialogOpen.value = false
    }


}


sealed interface InicioEvent {
    data object NavigateToPlayer : InicioEvent
//    data class InteractionTwo(val value: String): InicioEvent
}