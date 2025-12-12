package com.example.melodyquest.feature.trackplayer.ui

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.melodyquest.core.ui.components.AppHeader
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.Countdown
import com.example.melodyquest.core.ui.icons.DoubleChevronArrow
import com.example.melodyquest.core.ui.icons.Gear
import com.example.melodyquest.core.ui.icons.Metronome
import com.example.melodyquest.core.ui.icons.Minus
import com.example.melodyquest.core.ui.icons.Pause
import com.example.melodyquest.core.ui.icons.Play
import com.example.melodyquest.core.ui.icons.Plus
import com.example.melodyquest.feature.trackplayer.viewmodel.FakeTrackPlayerViewModel
import com.example.melodyquest.feature.trackplayer.viewmodel.ITrackPlayerViewModel
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerEvent
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerPublicEvent
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerState
import com.example.melodyquest.feature.trackplayer.viewmodel.TrackPlayerViewModel



@Preview(
    device = "spec:width=640dp,height=360dp,dpi=480",
)
@Composable
fun TrackPlayerScreenPreview(fakeVM: ITrackPlayerViewModel = FakeTrackPlayerViewModel()) {

    TrackPlayerScreen(
        viewModel = fakeVM,
        trackId = "",
        isLibrary = false,
        onNavigateBack = {}
    )
}

@Preview(
    device = "spec:width=640dp,height=360dp,dpi=480",
)
@Composable
fun TrackPlayerScreenPreview2(fakeVM: ITrackPlayerViewModel = FakeTrackPlayerViewModel(true)) {

    TrackPlayerScreen(
        viewModel = fakeVM,
        trackId = "",
        isLibrary = true,
        onNavigateBack = {}
    )
}



@Composable
fun TrackPlayerScreen(
    viewModel: ITrackPlayerViewModel = hiltViewModel<TrackPlayerViewModel>(),
    trackId: String,
    isLibrary: Boolean,
    onNavigateBack: () -> Unit
) {

    val activity = LocalActivity.current
    val state by viewModel.state.collectAsState()
    val isPlayerReady by viewModel.isReady.collectAsState()
    val onEvent = viewModel::onEvent

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.publicEvents.collect { event ->
                when (event) {
                    TrackPlayerPublicEvent.NavigateBack -> {
                        viewModel.release()
                        onNavigateBack()
                    }
                }
            }
        }
    }

//    LaunchedEffect(viewModel) {
//
//
//        viewModel.publicEvents.collect { event ->
//            when (event) {
//                TrackPlayerPublicEvent.NavigateBack -> {
////                    viewModel.release()
//                    onNavigateBack()
//                }
//            }
//        }
//    }

    LaunchedEffect(Unit) { Log.d("TrackPlayer", "VM instance: ${System.identityHashCode(viewModel)}") }



    DisposableEffect(viewModel) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        Log.d("TrackPlayer", "TrackPlayerScreen ON_DISPOSE called")
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            viewModel.release()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AppHeader(state.trackname, "Atrás", { onEvent(TrackPlayerEvent.NavigateBack) })



        /* Acordes */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyRow(

                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
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
                                .fillMaxHeight()


                        ) {
                            Text(viewModel.getDisplayChordName(chordConfig))
                        }
                    }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clickable {
                                    onEvent(TrackPlayerEvent.AddChord)
                                }
                                .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                                .padding(0.dp, 24.dp)
                                .width(160.dp)
                                .fillMaxHeight()


                        ) {
                            Text("+")
                        }
                    }

                }
            }

            PlayerControls(
                isPlaying = state.isPlaying,
                areExtraOptionsShown = state.areExtraOptionsShown,
                state = state,
                metronomeEnabled = state.trackConfiguration.metronomeEnabled,
                onPlayPauseClick = { onEvent(TrackPlayerEvent.PlayOrPause) },
                onTransposeUp = { onEvent(TrackPlayerEvent.TransposeUp) },
                onTransposeDown = { onEvent(TrackPlayerEvent.TransposeDown) },
                onToggleMetronome = { onEvent(TrackPlayerEvent.ToggleMetronome) },
                onToggleExtraOptions = { onEvent(TrackPlayerEvent.ToggleExtraOptions) },
                onCountIn = { onEvent(TrackPlayerEvent.ToggleMetronomeCountIn) },
                onBpmChange = { onEvent(TrackPlayerEvent.ChangeBpm(it)) },
                onEvent = onEvent
            )

    }

    if (state.showChordEditionDialog) {
        ChordEditionDialog(state, onEvent)
    }

}

@Composable
fun PlayerControls(
    isPlaying: Boolean,
    areExtraOptionsShown: Boolean,
    state: TrackPlayerState,
    metronomeEnabled: Boolean,
    onPlayPauseClick: () -> Unit,
    onTransposeUp: () -> Unit,
    onTransposeDown: () -> Unit,
    onToggleMetronome: () -> Unit,
    onToggleExtraOptions: () -> Unit,
    onBpmChange: (Int) -> Unit,
//    onTransposeChange: (Int) -> Unit,
    onCountIn: () -> Unit,
    onEvent: (TrackPlayerEvent) -> Unit
) {


    val animatedOffsetY by animateDpAsState(
        targetValue = if (areExtraOptionsShown) (-96).dp else 64.dp,
        label = "yOffset"
    )


    /* Controles */
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
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
//                Box(
//                    modifier = Modifier
//                        .clickable { onTransposeUp() }
//                        .padding(16.dp, 8.dp)
//                ) {
//                    Icon(
//                        AppIcons.Plus,
//                        contentDescription = "Transponer +1",
//                        modifier = Modifier
//                            .size(24.dp)
//                    )
//                }
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

        Box(
            contentAlignment = Alignment.Center,
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
                        .clickable { onToggleExtraOptions() }
                        .padding(16.dp, 8.dp)
                ) {
                    Icon(
                        AppIcons.Gear,
                        contentDescription = "Más opciones",
                        tint = if (metronomeEnabled) Color.Black else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }

            Column(
               modifier = Modifier
                   .layout { measurable, constraints ->
                       val placeable = measurable.measure(
                           constraints.copy(
                               minWidth = constraints.maxWidth,
                               maxWidth = constraints.maxWidth
                           )
                       )

                       layout(0, 0) {
                           placeable.placeRelative(
//                               x = (constraints.maxWidth - placeable.width) / 2,
//                               y = (constraints.maxHeight - placeable.height) / 2
                               x = -1 * constraints.maxWidth / 2,
                               y = 0
                           )
                       }
                   }
                   .height(128.dp)
                   .offset(0.dp, animatedOffsetY)
                   .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                   .background(Color.White)
                   .padding(16.dp, 8.dp)


            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onToggleExtraOptions()
                        }
                ) {
                    Icon(
                        AppIcons.DoubleChevronArrow,
                        contentDescription = "Más opciones",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(16.dp, 16.dp)
                            .graphicsLayer(
                                scaleX = 1f,
                                scaleY = 8f,
                                rotationZ = 90f
                            )
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text("BPM")

                        Spacer(Modifier.height(8.dp))

                        BasicTextField(
                            value = state.trackConfiguration.bpm.toString(),
                            onValueChange = {onBpmChange(it.toInt())},
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp, 8.dp)

                                ) {
                                    innerTextField()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, Color.Black, shape = RoundedCornerShape(4.dp))

                        )

                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text("Transponer")
                        Spacer(Modifier.height(8.dp))
                        Row(
                            Modifier
//                                .padding(8.dp, 0.dp)
                        ) {
                            Box(
                                Modifier
                                    .size(24.dp)
                                    .border(2.dp, Color.Black, shape = RoundedCornerShape(4.dp))
                                    .padding(4.dp)
                            ) {
                                Icon(
                                    AppIcons.Minus,
                                    contentDescription = "Transponer -1",
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Box(
                                Modifier
                                    .size(24.dp)
                                    .border(2.dp, Color.Black, shape = RoundedCornerShape(4.dp))
                                    .padding(4.dp)
                            ) {
                                Icon(
                                    AppIcons.Plus,
                                    contentDescription = "Transponer +1",
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                            }

                        }
                    }
                }

            }
        }
    }
}