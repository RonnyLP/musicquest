package com.example.melodyquest.feature.home.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject


interface TrackQuickEditor {
    fun setName(name: String) {}
    fun setData(data: TrackConfiguration) {}
}

interface IInicioTabViewModel {
    val tracks: StateFlow<List<Track>>
    val events: SharedFlow<InicioEvent>
    fun navigateToPlayer(id: String) {}

    val trackEditor: TrackQuickEditor


    val tempTrack: State<Track>
    var tempName: State<String>

    val isAddDialogOpen: State<Boolean>
    fun openAddDialog() {}
    fun closeAddDialog() {}
    fun submitAddDialog() {}

    val isEditDialogOpen: State<Boolean>
    fun openEditDialog(trackId: String) {}
    fun closeEditDialog() {}
    fun submitEditDialog() {}
    fun submitDeleteDialog() {}
}

@HiltViewModel
class InicioTabViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val trackRepository: TrackRepository
) : ViewModel(), IInicioTabViewModel {


    private val email: String? = authRepository.getCurrentUser()?.email


    override val tracks: StateFlow<List<Track>> = if (email != null) {
        trackRepository.getTracksFlow(email)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    } else {
        MutableStateFlow(emptyList())
    }


    override val tempTrack = mutableStateOf(Track(
        id = "",
        name = "",
        data = TrackConfiguration(),
        ownerEmail = email ?: ""
    ))


    override var tempName: State<String> = mutableStateOf("")


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

    override fun navigateToPlayer(id: String) {
        Log.d("ViewModelEvent", "Triggering Navigate to player")
        val emitted = _events.tryEmit(InicioEvent.NavigateToPlayer)
        Log.d("ViewModelEvent", "Event emission success: $emitted") // <<< AÑADE ESTO
        if (!emitted) {
            Log.e("ViewModelEvent", "Failed to emit NavigateToPlayer event. No active collectors or buffer full?")
        }
    }

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
        val foundTrack = tracks.value.find { it.id == trackId }
        println("Found track: ${foundTrack?.id}")
        if (foundTrack == null) {
            isEditDialogOpen.value = false
            return
        }

        val configCopy = Json.decodeFromString<TrackConfiguration>(
            Json.encodeToString(foundTrack.data)
        )

        tempTrack.value = foundTrack.copy(
            data = configCopy
        )
        println("TempTrack.id = ${tempTrack.value.id}")
        println("TempTrack.id = ${tempTrack.value.name}")

    }
    override fun closeEditDialog() {
        isEditDialogOpen.value = false
        resetTempTrack()
    }

    override fun submitAddDialog() {
        viewModelScope.launch {
            email?.let { trackRepository.addTrack(it, tempTrack.value.name, tempTrack.value.data) }
            resetTempTrack()
            closeAddDialog()
        }

    }


    override val trackEditor = object : TrackQuickEditor {
        override fun setName(name: String) {
            tempTrack.value = tempTrack.value.copy(name = name)
        }

        override fun setData(data: TrackConfiguration) {
            tempTrack.value = tempTrack.value.copy(data = data)
        }
    }

    override fun submitEditDialog() {
        viewModelScope.launch {
            if (email == null) return@launch
            trackRepository.updateTrack(email, tempTrack.value)
            resetTempTrack()
        }
        closeEditDialog()
    }

    override fun submitDeleteDialog() {
        viewModelScope.launch {
            if (email == null) return@launch
            trackRepository.deleteTrack(email, tempTrack.value)
            resetTempTrack()
        }
        closeEditDialog()
    }


    fun resetTempTrack() {
        tempTrack.value = Track(
            id = "",
            name = "",
            ownerEmail = email ?: "",
            data = TrackConfiguration()
        )
    }



}


sealed interface InicioEvent {
    data object NavigateToPlayer : InicioEvent
//    data class InteractionTwo(val value: String): InicioEvent
}



class FakeInicioTabViewModel(
    openAddDialog: Boolean = false,
    editAddDialog: Boolean = false
): ViewModel(), IInicioTabViewModel {
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
    override val tempTrack = mutableStateOf(Track(
        id = "",
        name = "",
        data = TrackConfiguration(),
        ownerEmail = ""
    ))
    override var tempName: State<String> = mutableStateOf("")
    override val events: SharedFlow<InicioEvent> = MutableSharedFlow()
    override val isAddDialogOpen: State<Boolean> = mutableStateOf(openAddDialog)
    override val isEditDialogOpen: State<Boolean> = mutableStateOf(editAddDialog)
    override val trackEditor: TrackQuickEditor = object : TrackQuickEditor {}
}
