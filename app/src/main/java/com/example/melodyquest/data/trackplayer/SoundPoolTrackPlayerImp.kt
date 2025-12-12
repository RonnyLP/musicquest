package com.example.melodyquest.data.trackplayer

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.melodyquest.R
import com.example.melodyquest.domain.model.ChordType
import com.example.melodyquest.domain.model.ChordTypes
import com.example.melodyquest.domain.model.Note
import com.example.melodyquest.domain.model.Notes
import com.example.melodyquest.domain.model.TrackConfiguration
import com.example.melodyquest.domain.model.TrackTimeline
import com.example.melodyquest.domain.trackplayer.TrackPlayerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

//class SoundPoolTrackPlayerImp @Inject constructor (
//    @ApplicationContext private val context: Context
//): TrackPlayerInterface {

class SoundPoolTrackPlayerImp (
    private val context: Context
): TrackPlayerInterface {

    override val isReady: StateFlow<Boolean> = TODO("Not yet implemented")

    private val soundPool: SoundPool
    private val noteToSoundId: MutableMap<Pair<Note, Int>, Int> = mutableMapOf()
    private val activeStreams = mutableListOf<Int>()

    init {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(attrs)
            .build()

        loadNotes(this.context)
    }

    private fun loadNotes(context: Context) {

        noteToSoundId[Notes.C  to 4] = soundPool.load(context, R.raw.c4, 1)
        noteToSoundId[Notes.Cs to 4] = soundPool.load(context, R.raw.cs4, 1)
        noteToSoundId[Notes.D  to 4] = soundPool.load(context, R.raw.d4, 1)
        noteToSoundId[Notes.Ds to 4] = soundPool.load(context, R.raw.ds4, 1)
        noteToSoundId[Notes.E  to 4] = soundPool.load(context, R.raw.e4, 1)
        noteToSoundId[Notes.F  to 4] = soundPool.load(context, R.raw.f4, 1)
        noteToSoundId[Notes.Fs to 4] = soundPool.load(context, R.raw.fs4, 1)
        noteToSoundId[Notes.G  to 4] = soundPool.load(context, R.raw.g4, 1)
        noteToSoundId[Notes.Gs to 4] = soundPool.load(context, R.raw.gs4, 1)
        noteToSoundId[Notes.A  to 4] = soundPool.load(context, R.raw.a4, 1)
        noteToSoundId[Notes.As to 4] = soundPool.load(context, R.raw.as4, 1)
        noteToSoundId[Notes.B  to 4] = soundPool.load(context, R.raw.b4, 1)
        noteToSoundId[Notes.C  to 5] = soundPool.load(context, R.raw.c5, 1)
        noteToSoundId[Notes.Cs to 5] = soundPool.load(context, R.raw.cs5, 1)
        noteToSoundId[Notes.D  to 5] = soundPool.load(context, R.raw.d5, 1)
        noteToSoundId[Notes.Ds to 5] = soundPool.load(context, R.raw.ds5, 1)
        noteToSoundId[Notes.E  to 5] = soundPool.load(context, R.raw.e5, 1)
        noteToSoundId[Notes.F  to 5] = soundPool.load(context, R.raw.f5, 1)
    }

    fun playChord(root: Note, octave: Int, type: ChordType): List<Int> {
        val streams = mutableListOf<Int>()

        type.intervals.forEach { interval ->
            val semitone = (root.semitone + interval) % 12
            val targetOctave = octave + (root.semitone + interval) / 12
            val note = Notes.allNotes.first { it.semitone == semitone }

            noteToSoundId[note to targetOctave]?.let { soundId ->
                val streamId = soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
                streams.add(streamId)
            }
        }

        return streams
    }



    private var job: Job? = null


    private var trackConfig: TrackConfiguration = TrackConfiguration();
    private var trackTimeline: TrackTimeline = TrackTimeline(emptyList(), 100)
//    private var trackTimeline: TrackTimeline? = null

    override fun setupTrack(trackConfiguration: TrackConfiguration) {
        trackConfig = trackConfiguration
        trackTimeline = TrackTimeline(
            trackConfiguration.progressionConfig,
            trackConfiguration.bpm
        )
    }

    override fun playTrack() {
        job?.cancel()

        job = CoroutineScope(Dispatchers.Default).launch {
            var timelineIterationStartReference = System.currentTimeMillis() + 100

            do {
                val startReference = timelineIterationStartReference

                trackTimeline.chordEvents.forEach { chordEv ->

                    val eventStart = startReference + chordEv.startTimeMs
                    while (System.currentTimeMillis() < eventStart) {
                        delay(1)
                    }
                    if (!isActive) return@launch
                    val streams = playChord(chordEv.root, chordEv.octave, ChordTypes.MAJOR)

//                    synchronized(activeStreams) {
//                        activeStreams.addAll(streams)
//                    }

                    // Detener acorde
                    launch {
                        delay(chordEv.durationMs)
                        synchronized(activeStreams) {
                            streams.forEach {
                                soundPool.stop(it)
                                activeStreams.remove(it)
                            }
                        }

                    }
                }
                timelineIterationStartReference += trackTimeline.totalDurationMs
                val immediatlyBeforeNextTimeline = timelineIterationStartReference - 100
                while (System.currentTimeMillis() < immediatlyBeforeNextTimeline) {
                    delay(1)
                }

            } while (isActive && trackConfig.shouldLoop)
        }
    }


    override fun stopTrack() {
        job?.cancel()
        job = null

        synchronized(activeStreams) {
            activeStreams.forEach { soundPool.stop(it) }
            activeStreams.clear()
        }
    }

    override fun pauseTrack() {
//        TODO("Not yet implemented")
        stopTrack()
    }

    override fun release() {
        soundPool.release()
    }


}