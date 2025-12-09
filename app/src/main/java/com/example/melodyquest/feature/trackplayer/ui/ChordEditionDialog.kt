package com.example.melodyquest.feature.trackplayer.ui

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.example.melodyquest.core.ui.components.GrayButton
import com.example.melodyquest.core.ui.components.GreenButton
import com.example.melodyquest.domain.model.ChordTypes
import com.example.melodyquest.domain.model.Notes
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerEvent
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerState

@Composable
fun ChordEditionDialog(
    state: TrackPlayerState,
    onEvent: (TrackPlayerEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(TrackPlayerEvent.DismissChordDialog) },

        ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
//                    .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp)

        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
//                        .fillMaxWidth()
            ) {
                AndroidView(
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = Notes.allNotes.size - 1
//                                value = viewModel.activeNoteIdx(viewModel.activeChord.value!!.root)
                            value = state.tempChordConfig!!.root.semitone
//                                displayedValues = viewModel.notesList.toTypedArray()
//                            displayedValues = state.noteNames.toTypedArray()
                            displayedValues = Notes.allNotes.map { it.getDisplayName(state.useFlat) }.toTypedArray()
                            setOnValueChangedListener { _, _, newNoteIdx ->
                                onEvent(TrackPlayerEvent.EditTempRoot(newNoteIdx))
                            }
                        }

                    }
                )
                AndroidView(
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = ChordTypes.entries.size - 1
//                            value = state.activeChordConfig!!.type.ordinal
                            value = ChordTypes.ordinal(state.tempChordConfig!!.type)
                            displayedValues = ChordTypes.entries.map{ it.chordName }.toTypedArray()
                            setOnValueChangedListener { _, _, newChordIdx ->
                                onEvent(TrackPlayerEvent.EditTempChordType(newChordIdx))
                            }
                        }

                    }
                )

                AndroidView(
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 1
                            maxValue = 8
                            value = state.tempChordConfig!!.durationBeats
                            setOnValueChangedListener { _, _, newDuration ->
                                onEvent(TrackPlayerEvent.EditTempDuration(newDuration))
                            }
                        }

                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                GrayButton("Cancelar", onClick = { onEvent(TrackPlayerEvent.DismissChordDialog) })
                GreenButton("Aceptar", onClick = { onEvent(TrackPlayerEvent.SuccessChordDialog) })
            }

        }
    }
}