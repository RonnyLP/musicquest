package com.example.melodyquest.feature.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.data.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.listOf

@HiltViewModel
class BibliotecaTabViewModel @Inject constructor(
    private val repository: LibraryRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<BibliotecaEvent>()
    val events = _events.asSharedFlow()

    fun navigateToPlayer() {
        _events.tryEmit(BibliotecaEvent.NavigateToPlayer)
    }

//    val popularSongs =  listOf(
//        "Hotel California - Verso",
//        "Canon de Pachenbel",
//        "Creep",
//    )
//
//    val commonProgressions =  listOf(
//        "Hotel California - Verso",
//        "Canon de Pachenbel",
//        "i - VIb - VIIb - i",
//    )

//    val popularSongs = repository.getPopularSongs()
//        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
//    val libraryTracks = repository.getLibraryTracks()
//        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    var libraryTracks by mutableStateOf(emptyList<Track>())
        private set

//    val commonProgressions = repository.getCommonProgressions()
//        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _isCloneDialogOpen = mutableStateOf(false)
    val isCloneDialogOpen: State<Boolean> = _isCloneDialogOpen

    fun openCloneDialog() {
        _isCloneDialogOpen.value = true
    }
    fun closeCloneDialog() {
        _isCloneDialogOpen.value = false
    }


    init {
        viewModelScope.launch {
            libraryTracks = repository.getLibraryTracks()
        }
    }


}


sealed class BibliotecaEvent {
    object NavigateToPlayer: BibliotecaEvent()
}