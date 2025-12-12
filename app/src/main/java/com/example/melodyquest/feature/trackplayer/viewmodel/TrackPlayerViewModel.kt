package com.example.melodyquest.feature.trackplayer.viewmodel

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.data.repository.TrackRepository
import com.example.melodyquest.domain.auth.AuthRepository
import com.example.melodyquest.domain.model.ChordConfig
import com.example.melodyquest.domain.model.ChordType
import com.example.melodyquest.domain.model.ChordTypes
import com.example.melodyquest.domain.model.Notes
import com.example.melodyquest.domain.model.TimeSignature
import com.example.melodyquest.domain.model.TrackConfiguration
import com.example.melodyquest.domain.trackplayer.TrackPlayerInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface ITrackPlayerViewModel {


    val isReady: StateFlow<Boolean>
    val state: StateFlow<TrackPlayerState>

    fun transposeTrack(offset: Int) {}

    fun playOrPause() {}

    fun startChordChanges(chordIdx: Int) {}
    fun saveChordChanges() {}
    fun cancelChordChanges() {}

    fun onEvent(event: TrackPlayerEvent) {}
    val publicEvents: SharedFlow<TrackPlayerPublicEvent>
    fun getDisplayChordName(chordConfig: ChordConfig): String = ChordTypes.entries[chordConfig.typeIdx].getAbbrName(chordConfig.root)
    fun release() {}
}


@HiltViewModel
class TrackPlayerViewModel @Inject constructor(
    private val trackPlayer: TrackPlayerInterface,
    private val repository: TrackRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
//    @ApplicationContext private val context: Context
): ViewModel(), ITrackPlayerViewModel {

    private val trackId: String = checkNotNull(savedStateHandle["id"])

    private val email: String = authRepository.getCurrentUser()?.email
        ?: throw IllegalStateException("No authenticated user")

    val track = MutableStateFlow<Track?>(null)

    private var _state = MutableStateFlow(TrackPlayerState())
    override val state = _state.asStateFlow()

    override val isReady = trackPlayer.isReady



    init {
        loadTrack()

        viewModelScope.launch {
            state
                .map { it.trackConfiguration } // solo nos interesan cambios del configuration
                .distinctUntilChanged()        // evita guardar si no cambió nada
                .collect { newConfig ->
                    val logConfig = newConfig.copy(progressionConfig = emptyList())

                    Log.d("TrackConfig", "Config changed: $logConfig")

                    saveConfigurationToRepository(newConfig)
                }
        }
    }


    fun loadTrack() {
        viewModelScope.launch {
            Log.d("TrackPlayerViewModel", "Loading track with ID: $trackId")
            val foundTrack = withContext(Dispatchers.IO) {
                repository.getTrackById(trackId)
            }

            if (foundTrack == null) return@launch
            track.value = foundTrack
            _state.value = _state.value.copy(
                trackConfiguration = foundTrack.data,
                trackname = foundTrack.name
            )
//            track.value = track.value.copy(
//                data = track.value?.data
//            )
        }
    }

    private suspend fun saveConfigurationToRepository(newConfig: TrackConfiguration) {
        val currentTrack = track.value ?: return

        val updatedTrack = currentTrack.copy(
            data = newConfig
        )

        withContext(Dispatchers.IO) {
            repository.updateTrack(email, updatedTrack)
        }

        Log.d("TrackPlayerVM", "Track saved with new configuration")
    }

//    private val _activeChord = mutableStateOf<ChordConfig?>(null)
//    val activeChord: State<ChordConfig?> = _activeChord

    override fun transposeTrack(offset: Int) {
        val currentTrackConfig = _state.value.trackConfiguration
        val newProgression = currentTrackConfig.progressionConfig.map { chordConfig ->
            val newRootSemitone = (chordConfig.root.semitone + offset).let {
                var result = it % 12
                if (result < 0) result += 12
                result
            }
            val newRoot = Notes.fromSemitone(newRootSemitone) ?: chordConfig.root
            chordConfig.copy(root = newRoot)
        }
        _state.value = _state.value.copy(
            trackConfiguration = currentTrackConfig.copy(progressionConfig = newProgression)
        )
    }




    override fun playOrPause() {
        if (this._state.value.isPlaying) {
            trackPlayer.pauseTrack()
        } else {
            trackPlayer.setupTrack(this._state.value.trackConfiguration)
            trackPlayer.playTrack()
        }
        _state.value = _state.value.copy(
            isPlaying = !_state.value.isPlaying
        )

    }

//    private var _editingChordIdx: Int? = null

    override fun startChordChanges(chordIdx: Int) {
        val originalChordConfig =_state.value.trackConfiguration.progressionConfig[chordIdx]
        _state.value = _state.value.copy(
            showChordEditionDialog = true,
            tempChordConfig = originalChordConfig.copy(),
            tempChordIdx = chordIdx
        )

    }

    override fun saveChordChanges() {
//        val originalTrackConfig = _state.value.trackConfiguration
//        val trackConfigCopy = originalTrackConfig.copy(
//            progressionConfig = originalTrackConfig.progressionConfig
//        )
        _state.value = _state.value.copy(
            showChordEditionDialog = false,
            trackConfiguration = _state.value.trackConfiguration.copy(
                progressionConfig = _state.value.trackConfiguration.progressionConfig
                    .mapIndexed { index, chord ->
                        if (index == _state.value.tempChordIdx) _state.value.tempChordConfig!! else chord
                    }
            ),
            tempChordConfig = null,
            tempChordIdx = null
        )
    }

    override fun cancelChordChanges() {
        _state.value = _state.value.copy(
            showChordEditionDialog = false,
            tempChordConfig = null,
            tempChordIdx = null
        )
    }




    private val _publicEvents = MutableSharedFlow<TrackPlayerPublicEvent>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val publicEvents = _publicEvents.asSharedFlow()

    override fun release() {
        trackPlayer.release()
    }

//    override fun getDisplayChordName(chordConfig: ChordConfig): String {
//        return chordConfig.type.getAbbrName(chordConfig.root)
//    }

    override fun onEvent(event: TrackPlayerEvent) {
        when (event) {
            TrackPlayerEvent.NavigateBack -> {
                val emitted = _publicEvents.tryEmit(TrackPlayerPublicEvent.NavigateBack)
                Log.d("TrackPlayerViewModel", "NavigateBack event emitted: $emitted")
            }
            TrackPlayerEvent.Pause -> {
                trackPlayer.pauseTrack()
            }
            TrackPlayerEvent.Play -> {
                trackPlayer.playTrack()
            }
            TrackPlayerEvent.Stop -> {
                trackPlayer.stopTrack()
            }
            TrackPlayerEvent.PlayOrPause -> {
                playOrPause()
            }
            TrackPlayerEvent.AddChord -> {
                _state.value = _state.value.copy(
                    trackConfiguration = _state.value.trackConfiguration.copy(
                        progressionConfig = _state.value.trackConfiguration.progressionConfig + ChordConfig(Notes.C, 4,
                            ChordTypes.ordinal(ChordTypes.MAJOR), 4)
                    )
                )
            }
            is TrackPlayerEvent.ShowChordDialog -> {
                startChordChanges(event.chordIdx)
            }
            is TrackPlayerEvent.EditTempRoot -> {
                _state.value = _state.value.copy(
                    tempChordConfig = _state.value.tempChordConfig?.copy(root = Notes.fromSemitone(event.rootIdx)!!)
                )
            }
            TrackPlayerEvent.ToggleMetronome -> {
                _state.value = _state.value.copy(
                    trackConfiguration = _state.value.trackConfiguration.copy(
                        metronomeEnabled = !_state.value.trackConfiguration.metronomeEnabled
                    )
                )
            }
            TrackPlayerEvent.ToggleMetronomeCountIn -> {
                _state.value = _state.value.copy(
                    trackConfiguration = _state.value.trackConfiguration.copy(
                        metronomeCountIn = !_state.value.trackConfiguration.metronomeCountIn
                    )
                )
            }
            TrackPlayerEvent.ToggleExtraOptions -> {
                _state.value = _state.value.copy(
                    areExtraOptionsShown = !_state.value.areExtraOptionsShown
                )
            }
            is TrackPlayerEvent.EditTempChordType -> {
                _state.value = _state.value.copy(
                    tempChordConfig = _state.value.tempChordConfig?.copy(typeIdx = event.chordTypeIdx)
                )
            }
            is TrackPlayerEvent.EditTempOctave -> {
                _state.value = _state.value.copy(
                    tempChordConfig = _state.value.tempChordConfig?.copy(octave = event.octave)
                )
            }
            is TrackPlayerEvent.EditTempDuration -> {
                _state.value = _state.value.copy(
                    tempChordConfig = _state.value.tempChordConfig?.copy(durationBeats = event.duration)
                )
            }
            is TrackPlayerEvent.UpdateTimeSignature -> {
                _state.value = _state.value.copy(
                    trackConfiguration = _state.value.trackConfiguration.copy(
                        timeSignature = TimeSignature.defaults[event.timeSignatureIdx]
                    )
                )
                _state.value = _state.value.copy(
                    timeSignatureDropdownEnabled = false
                )
            }
            is TrackPlayerEvent.ChangeBpm -> {

                _state.value = _state.value.copy(
                    trackConfiguration = _state.value.trackConfiguration.copy(
                        bpm = event.bpm
                    )
                )
            }

            TrackPlayerEvent.ShowTimeSignatureDropdown -> {
                _state.value = _state.value.copy(
                    timeSignatureDropdownEnabled = true
                )
            }
            TrackPlayerEvent.DismissTimeSignatureDropdown -> {
                _state.value = _state.value.copy(
                    timeSignatureDropdownEnabled = false
                )
            }



            TrackPlayerEvent.SuccessChordDialog -> {
                saveChordChanges()
            }
            TrackPlayerEvent.DismissChordDialog -> {
                cancelChordChanges()
            }
            TrackPlayerEvent.TransposeDown -> {
                transposeTrack(-1)
            }
            TrackPlayerEvent.TransposeUp -> {
                transposeTrack(1)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TrackPlayerViewModel", "onCleared called")
        trackPlayer.release()
    }

}

sealed class TrackPlayerEvent {
    object Play : TrackPlayerEvent()
    object Pause : TrackPlayerEvent()
    object Stop : TrackPlayerEvent()
    object PlayOrPause : TrackPlayerEvent()
    data class ShowChordDialog(val chordIdx: Int) : TrackPlayerEvent()
//    data class EditTempChord(val chordConfig: ChordConfig) : TrackPlayerEvent()
    data class EditTempRoot(val rootIdx: Int): TrackPlayerEvent()
    data class EditTempChordType(val chordTypeIdx: Int): TrackPlayerEvent()
    data class EditTempOctave(val octave: Int): TrackPlayerEvent()
    data class EditTempDuration(val duration: Int): TrackPlayerEvent()
    data class UpdateTimeSignature(val timeSignatureIdx: Int) : TrackPlayerEvent()
    object ShowTimeSignatureDropdown : TrackPlayerEvent()
    object DismissTimeSignatureDropdown : TrackPlayerEvent()

    object SuccessChordDialog : TrackPlayerEvent()
    object DismissChordDialog : TrackPlayerEvent()
    object NavigateBack : TrackPlayerEvent()
    object TransposeUp : TrackPlayerEvent()
    object TransposeDown : TrackPlayerEvent()
    object AddChord : TrackPlayerEvent()

    object ToggleMetronome : TrackPlayerEvent()
    object ToggleExtraOptions : TrackPlayerEvent()
    object ToggleMetronomeCountIn : TrackPlayerEvent()

    data class ChangeBpm(val bpm: Int) : TrackPlayerEvent()

}

sealed class TrackPlayerPublicEvent {
    object NavigateBack : TrackPlayerPublicEvent()
}

data class TrackPlayerState (
    var isPlaying: Boolean = false,
    var trackname: String = "",
    var areExtraOptionsShown: Boolean = false,
    var trackConfiguration: TrackConfiguration = TrackConfiguration(
        progressionConfig = emptyList()
    ),
    var tempChordConfig: ChordConfig? = null,
    var tempChordIdx: Int? = null,
//    var noteNames: List<String> = emptyList(),
//    var chordNames: List<String> = emptyList(),
    var timeSignatures: List<TimeSignature> = TimeSignature.defaults,
    var showChordEditionDialog: Boolean = false,
    var useFlat: Boolean = false,
    val timeSignatureDropdownEnabled: Boolean = false
)


class FakeTrackPlayerViewModel(
    showExtraOptions: Boolean = false
): ViewModel(), ITrackPlayerViewModel {

    private val _state = MutableStateFlow(TrackPlayerState(
        areExtraOptionsShown = showExtraOptions,
        trackConfiguration = TrackConfiguration(
            progressionConfig = listOf(
                ChordConfig(
                    root = Notes.C,
                    typeIdx = ChordTypes.ordinal(ChordTypes.MAJOR),
                    octave = 4,
                    durationBeats = 4
                ),
                ChordConfig(
                    root = Notes.E,
                    typeIdx = ChordTypes.ordinal(ChordTypes.MAJOR),
                    octave = 4,
                    durationBeats = 4
                )
            )
        )
    ))
    override val state = _state.asStateFlow()

    override val isReady = MutableStateFlow(true)

    override val publicEvents = MutableSharedFlow<TrackPlayerPublicEvent>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )


}