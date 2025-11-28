package com.example.melodyquest.feature.trackplayer.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.melodyquest.core.ui.components.AppHeader
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.Countdown
import com.example.melodyquest.core.ui.icons.Metronome
import com.example.melodyquest.core.ui.icons.Minus
import com.example.melodyquest.core.ui.icons.Pause
import com.example.melodyquest.core.ui.icons.Play
import com.example.melodyquest.core.ui.icons.Plus
import com.example.melodyquest.data.trackplayer.TrackPlayerFakeImp
import com.example.melodyquest.domain.model.TrackConfiguration
import com.example.melodyquest.domain.trackplayer.TrackPlayerInterface
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerEvent
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerPublicEvent
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerState
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerViewModel


//class FakeTrackPlayer: TrackPlayerInterface {
//    override val isReady = TODO("Not yet")
//    override fun release() {
//        TODO("Not yet implemented")
//    }
//
//    override fun playTrack() {
//        TODO("Not yet implemented")
//    }
//
//    override fun pauseTrack() {
//        TODO("Not yet implemented")
//    }
//
//    override fun setupTrack(trackConfiguration: TrackConfiguration) {
//        TODO("Not yet implemented")
//    }
//
//    override fun stopTrack() {
//        TODO("Not yet implemented")
//    }
//}


// Landscape
//@Preview(
//    device = "spec:width=640dp,height=360dp,dpi=480",
////    device = "spec:width=360dp,height=640dp,dpi=480",
//)
//@Composable
//fun TrackPlayerScreenPreview() {
//    val fakeTrackPlayer = TrackPlayerFakeImp(LocalContext.current)
//    val fakeViewModel = remember { TrackPlayerViewModel(trackPlayer = fakeTrackPlayer) }
//
////    LaunchedEffect(Unit) {
////        fakeViewModel.onEvent(TrackPlayerEvent.ShowChordDialog(0))
////    }
//    TrackPlayerScreen(
//        viewModel = fakeViewModel,
//        onNavigateBack = {}
//    )
//}
//

@Composable
fun TrackPlayerScreen(
    viewModel: TrackPlayerViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val activity = LocalActivity.current
    val state by viewModel.state.collectAsState()
    val isPlayerReady by viewModel.isReady.collectAsState()
    val onEvent = viewModel::onEvent

    LaunchedEffect(Unit) {


        viewModel.publicEvents.collect { event ->
            when (event) {
                TrackPlayerPublicEvent.NavigateBack -> {
                    onNavigateBack()
                }
            }
        }
    }




    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AppHeader("Título de la canción", "Atrás", { onEvent(TrackPlayerEvent.NavigateBack) })



        /* Acordes */
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Text(if (isPlayerReady) "TrackPlayer isReady: $isPlayerReady" else "TrackPlayer isReady: $isPlayerReady")

            LazyRow(

                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(32.dp)
            ) {

                itemsIndexed(state.trackConfiguration.progressionConfig) { idx, chordConfig ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clickable {
                                onEvent(TrackPlayerEvent.ShowChordDialog(idx))
                            }
                            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                            .padding(0.dp, 24.dp)
                            .width(160.dp)


                    ) {
                        Text(viewModel.getDisplayChordName(chordConfig))
                    }
                }

            }

            if (isPlayerReady) {
                PlayerControls(
                    isPlaying = state.isPlaying,
                    state = state,
                    metronomeEnabled = state.trackConfiguration.metronomeEnabled,
                    onPlayPauseClick = { onEvent(TrackPlayerEvent.PlayOrPause) },
                    onTransposeUp = { onEvent(TrackPlayerEvent.TransposeUp) },
                    onTransposeDown = { onEvent(TrackPlayerEvent.TransposeDown) },
                    onToggleMetronome = { onEvent(TrackPlayerEvent.ToggleMetronome) },
                    onCountIn = { onEvent(TrackPlayerEvent.ToggleMetronomeCountIn) },
                    onEvent = onEvent
                )
            } else {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize().height(72.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
        }



    }

    if (state.showChordEditionDialog) {
        ChordEditionDialog(state, onEvent)
    }

}

@Composable
fun PlayerControls(
    isPlaying: Boolean,
    state: TrackPlayerState,
    metronomeEnabled: Boolean,
    onPlayPauseClick: () -> Unit,
    onTransposeUp: () -> Unit,
    onTransposeDown: () -> Unit,
    onToggleMetronome: () -> Unit,
    onCountIn: () -> Unit,
    onEvent: (TrackPlayerEvent) -> Unit
) {
    /* Controles */
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 8.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier
                        .clickable { onToggleMetronome() }
                        .padding(16.dp, 8.dp)
                ) {
                    Icon(
                        AppIcons.Metronome,
                        contentDescription = "Transponer -1",
                        tint = if (metronomeEnabled) Color.Black else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clickable { onCountIn() }
                        .padding(16.dp, 8.dp)
                ) {
                    Icon(
                        AppIcons.Countdown,
                        contentDescription = "Count in",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clickable { onTransposeUp() }
                        .padding(16.dp, 8.dp)
                ) {
                    Icon(
                        AppIcons.Plus,
                        contentDescription = "Transponer +1",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .clickable { onPlayPauseClick() }
                .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(16.dp, 8.dp)
        ) {
            Icon(
                if (!isPlaying) AppIcons.Play else AppIcons.Pause,
                contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                modifier = Modifier
                    .size(32.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .weight(1f)
                .padding(24.dp, 0.dp)
        ) {
            Box {
                OutlinedButton(onClick = { onEvent(TrackPlayerEvent.ShowTimeSignatureDropdown) }) {
                    Text(state.trackConfiguration.timeSignature.toDisplayString())
                }
                DropdownMenu(
                    expanded = state.timeSignatureDropdownEnabled,
                    onDismissRequest = { onEvent(TrackPlayerEvent.DismissTimeSignatureDropdown) }
                ) {
                    state.timeSignatures.forEachIndexed { i, timeSignature ->
                        DropdownMenuItem(
                            text = { Text(timeSignature.toDisplayString()) },
                            onClick = {
                                onEvent(TrackPlayerEvent.DismissTimeSignatureDropdown)
                                onEvent(
                                    TrackPlayerEvent.UpdateTimeSignature(i)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}