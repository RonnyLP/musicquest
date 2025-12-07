package com.example.melodyquest.feature.home.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.data.repository.TrackRepository
import com.example.melodyquest.domain.auth.AuthRepository
import com.example.melodyquest.domain.model.TrackConfiguration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

interface IInicioTabViewModel {
    val tracks: StateFlow<List<Track>>
    val events: SharedFlow<InicioEvent>
    fun navigateToPlayer() {}

    val progresionesGuardadas: List<String>

    val isAddDialogOpen: State<Boolean>
    fun openAddDialog() {}
    fun closeAddDialog() {}

    val isEditDialogOpen: State<Boolean>
    fun openEditDialog(trackId: String) {}
    fun closeEditDialog() {}
}

@HiltViewModel
class InicioTabViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val trackRepository: TrackRepository
) : ViewModel(), IInicioTabViewModel {

    /* Eventos de navegación */

    private val email: String? = authRepository.getCurrentUser()?.email


    override val tracks: StateFlow<List<Track>> = if (email != null) {
        trackRepository.getTracksFlow(email)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    } else {
        MutableStateFlow(emptyList())
    }
//        trackRepository.getAllTracks(email!!)


    init {
        // Sync inicial: Firestore → Room
        viewModelScope.launch {
            email?.let { trackRepository.syncTracks(it) }
        }
    }

    private val _events = MutableSharedFlow<InicioEvent>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val events = _events.asSharedFlow()

    override fun navigateToPlayer() {
        Log.d("ViewModelEvent", "Triggering Navigate to player")
        val emitted = _events.tryEmit(InicioEvent.NavigateToPlayer)
        Log.d("ViewModelEvent", "Event emission success: $emitted") // <<< AÑADE ESTO
        if (!emitted) {
            Log.e("ViewModelEvent", "Failed to emit NavigateToPlayer event. No active collectors or buffer full?")
        }
    }

    override val progresionesGuardadas = listOf(
        "Everlong",
        "Canon de Pachenbel",
        "Creep",
        "Misplaced",
    )

//    val _progressionList: StateFlow<List<Track>> = trackRepository.getAllTracks()
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    override val isAddDialogOpen = mutableStateOf(false)

    override fun openAddDialog() {
        isAddDialogOpen.value = true
    }
    override fun closeAddDialog() {
        isAddDialogOpen.value = false
    }

    override val isEditDialogOpen = mutableStateOf(false)

    override fun openEditDialog(trackId: String) {
        isEditDialogOpen.value = true
    }
    override fun closeEditDialog() {
        isEditDialogOpen.value = false
    }


}


sealed interface InicioEvent {
    data object NavigateToPlayer : InicioEvent
//    data class InteractionTwo(val value: String): InicioEvent
}



class FakeInicioTabViewModel : ViewModel(), IInicioTabViewModel {
    override val tracks: StateFlow<List<Track>> = MutableStateFlow(
        listOf(
            Track(
                id = "1",
                ownerEmail = "fake@user.com",
                name = "Everlong",
                data = TrackConfiguration(),
            ),
            Track(
                id = "2",
                ownerEmail = "fake@user.com",
                name = "Creep",
                data = TrackConfiguration(),
            )
        )
    )
    override val events: SharedFlow<InicioEvent> = MutableSharedFlow()
    override val progresionesGuardadas: List<String> = listOf("Everlong", "Canon de Pachenbel", "Creep", "Misplaced")
    override val isAddDialogOpen: State<Boolean> = mutableStateOf(false)
    override val isEditDialogOpen: State<Boolean> = mutableStateOf(false)
}
