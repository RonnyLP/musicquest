package com.example.melodyquest.data.trackplayer

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.util.Log
import com.example.melodyquest.R
import com.example.melodyquest.domain.model.ChordTypes
import com.example.melodyquest.domain.model.Note
import com.example.melodyquest.domain.model.TimeSignature
import com.example.melodyquest.domain.model.Notes
import com.example.melodyquest.domain.model.TrackConfiguration
import com.example.melodyquest.domain.model.TrackTimeline
import com.example.melodyquest.domain.trackplayer.TrackPlayerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class AudioTrackTrackPlayerImp(
    private val context: Context
): TrackPlayerInterface {



    private val noteToSoundId: MutableMap<Pair<Note, Int>, ShortArray> = mutableMapOf()
    private var _isReady = MutableStateFlow(false)
    override val isReady = _isReady.asStateFlow()

    private var metronomeHighAudioTrack: AudioTrack? = null
    private var metronomeLowAudioTrack: AudioTrack? = null

    init  {
        loadNotes(this.context)


        _isReady.value = true
    }

    private var job: Job? = null


    private fun loadNotes(context: Context) {

        noteToSoundId[Notes.C  to 4] = this.loadWav(context, R.raw.c4)
        noteToSoundId[Notes.Cs to 4] = this.loadWav(context, R.raw.cs4)
        noteToSoundId[Notes.D  to 4] = this.loadWav(context, R.raw.d4)
        noteToSoundId[Notes.Ds to 4] = this.loadWav(context, R.raw.ds4)
        noteToSoundId[Notes.E  to 4] = this.loadWav(context, R.raw.e4)
        noteToSoundId[Notes.F  to 4] = this.loadWav(context, R.raw.f4)
        noteToSoundId[Notes.Fs to 4] = this.loadWav(context, R.raw.fs4)
        noteToSoundId[Notes.G  to 4] = this.loadWav(context, R.raw.g4)
        noteToSoundId[Notes.Gs to 4] = this.loadWav(context, R.raw.gs4)
        noteToSoundId[Notes.A  to 4] = this.loadWav(context, R.raw.a4)
        noteToSoundId[Notes.As to 4] = this.loadWav(context, R.raw.as4)
        noteToSoundId[Notes.B  to 4] = this.loadWav(context, R.raw.b4)
        noteToSoundId[Notes.C  to 5] = this.loadWav(context, R.raw.c5)
        noteToSoundId[Notes.Cs to 5] = this.loadWav(context, R.raw.cs5)
        noteToSoundId[Notes.D  to 5] = this.loadWav(context, R.raw.d5)
        noteToSoundId[Notes.Ds to 5] = this.loadWav(context, R.raw.ds5)
        noteToSoundId[Notes.E  to 5] = this.loadWav(context, R.raw.e5)
        noteToSoundId[Notes.F  to 5] = this.loadWav(context, R.raw.f5)

        val originalHighPcm = loadWav(context, R.raw.metronome_tick)
        val originalLowPcm = loadWav(context, R.raw.metronome_tock)

        Log.e("DEBUG_PCM", "originalHighPcm size = ${originalHighPcm.size}")
        Log.e("DEBUG_PCM", "originalLowPcm size = ${originalLowPcm.size}")

        val envelopedHighPcm = applyEnvelope(originalHighPcm, attackMs = 1L, releaseMs = 50L)
        val envelopedLowPcm = applyEnvelope(originalLowPcm, attackMs = 1L, releaseMs = 50L)

        val adjustedHighPcm = applyVolume(envelopedHighPcm, 0.5f)
        val adjustedLowPcm = applyVolume(envelopedLowPcm, 0.5f)

        Log.e("DEBUG_PCM", "high size = ${adjustedHighPcm.size}")
        Log.e("DEBUG_PCM", "low size = ${adjustedLowPcm.size}")

        metronomeHighAudioTrack = createStaticAudioTrack(adjustedHighPcm)
        metronomeLowAudioTrack = createStaticAudioTrack(adjustedLowPcm)

        Log.e("DEBUG_METRONOME", "metronomeHighAudioTrack state = ${metronomeHighAudioTrack?.state}")
        Log.e("DEBUG_METRONOME", "metronomeLowAudioTrack state = ${metronomeLowAudioTrack?.state}")
        Log.e("DEBUG_METRONOME", "HIGH is null? ${metronomeHighAudioTrack == null}")
        Log.e("DEBUG_METRONOME", "LOW is null? ${metronomeLowAudioTrack == null}")

    }

    private fun applyVolume(pcmData: ShortArray, volumeFactor: Float): ShortArray {
        val adjustedPcm = ShortArray(pcmData.size)
        for (i in pcmData.indices) {
            adjustedPcm[i] = (pcmData[i] * volumeFactor).toInt().toShort()
        }
        return adjustedPcm
    }


    private fun decodeMp3ToPcm(resId: Int): ShortArray {

        val extractor = MediaExtractor()

        val afd = context.resources.openRawResourceFd(resId)
        extractor.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        val format = extractor.getTrackFormat(0)
        extractor.selectTrack(0)

        val mime = format.getString(MediaFormat.KEY_MIME) ?: return shortArrayOf()
        val codec = MediaCodec.createDecoderByType(mime)
        codec.configure(format, null, null, 0)
        codec.start()

        val output = ByteArrayOutputStream()
        val bufferInfo = MediaCodec.BufferInfo()

        var sawInputEOS = false
        var sawOutputEOS = false

        while (!sawOutputEOS) {
            if (!sawInputEOS) {
                val inputBufferIndex = codec.dequeueInputBuffer(10000)
                if (inputBufferIndex >= 0) {
                    val inputBuffer = codec.getInputBuffer(inputBufferIndex)!!
                    val sampleSize = extractor.readSampleData(inputBuffer, 0)
                    if (sampleSize < 0) {
                        codec.queueInputBuffer(
                            inputBufferIndex, 0, 0, 0,
                            MediaCodec.BUFFER_FLAG_END_OF_STREAM
                        )
                        sawInputEOS = true
                    } else {
                        codec.queueInputBuffer(
                            inputBufferIndex, 0, sampleSize,
                            extractor.sampleTime, 0
                        )
                        extractor.advance()
                    }
                }
            }

            val outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 10000)
            if (outputBufferIndex >= 0) {
                val outputBuffer = codec.getOutputBuffer(outputBufferIndex)!!
                val chunk = ByteArray(bufferInfo.size)
                outputBuffer.get(chunk)
                outputBuffer.clear()
                output.write(chunk)
                codec.releaseOutputBuffer(outputBufferIndex, false)
                if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                    sawOutputEOS = true
                }
            }
        }

        codec.stop()
        codec.release()
        extractor.release()

        val bytes = output.toByteArray()
        val shorts = ShortArray(bytes.size / 2)
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts)
        return shorts

    }


    fun loadWav(context: Context, resId: Int): ShortArray {
        val input = context.resources.openRawResource(resId)
        val wav = input.readBytes()
        // Cabecera WAV de 44 bytes
        val pcmData = wav.copyOfRange(44, wav.size)
        val shorts = ShortArray(pcmData.size / 2)
        ByteBuffer.wrap(pcmData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts)
        return shorts
    }


    private fun mixSamples(samples: List<ShortArray>): ShortArray {
        val maxLength = samples.maxOf { it.size }
        val mixed = ShortArray(maxLength)
        for (i in mixed.indices) {
            var sum = 0
            for (s in samples) {
                if (i < s.size) sum += s[i].toInt()
            }
            mixed[i] = sum.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }
        return mixed
    }

    private fun applyEnvelope(pcmData: ShortArray, attackMs: Long, releaseMs: Long, sampleRate: Int = 44100): ShortArray {
        // Convierte duraciones de ms a número de muestras
        val attackSamples = (sampleRate * attackMs / 1000).toInt()
        val releaseSamples = (sampleRate * releaseMs / 1000).toInt()

        val totalSamples = pcmData.size
        val envelopedPcm = ShortArray(totalSamples)

        // 1. Fase de Ataque (Fade In)
        for (i in 0 until minOf(attackSamples, totalSamples)) {
            val multiplier = i.toFloat() / attackSamples // Va de 0.0 a 1.0
            envelopedPcm[i] = (pcmData[i] * multiplier).toInt().toShort()
        }

        // 2. Fase de Sostenido (Volumen máximo)
        val sustainStart = attackSamples
        val sustainEnd = (totalSamples - releaseSamples).coerceAtLeast(sustainStart)
        for (i in sustainStart until sustainEnd) {
            envelopedPcm[i] = pcmData[i] // El volumen es 1.0
        }

        // 3. Fase de Liberación (Fade Out)
        val releaseStart = (totalSamples - releaseSamples).coerceAtLeast(0)
        for (i in releaseStart until totalSamples) {
            val samplesIntoRelease = i - releaseStart
            val multiplier = 1.0f - (samplesIntoRelease.toFloat() / releaseSamples) // Va de 1.0 a 0.0
            envelopedPcm[i] = (pcmData[i] * multiplier).toInt().toShort()
        }

        return envelopedPcm
    }


    private var trackConfig: TrackConfiguration = TrackConfiguration();
    private var trackTimeline: TrackTimeline = TrackTimeline(emptyList(), 100)
    private val chordAudioTracks = mutableListOf<AudioTrack>()

    val availableTimeSignatures: List<TimeSignature> = TimeSignature.defaults





    override fun setupTrack(trackConfiguration: TrackConfiguration) {
        job?.cancel()
        chordAudioTracks.forEach {
            it.release()
        }
        chordAudioTracks.clear()
        metronomeHighAudioTrack?.release()
        metronomeLowAudioTrack?.release()

        val originalHighPcm = loadWav(context, R.raw.metronome_tick)
        val originalLowPcm = loadWav(context, R.raw.metronome_tock)
        val envelopedHighPcm = applyEnvelope(originalHighPcm, attackMs = 1L, releaseMs = 50L)
        val envelopedLowPcm = applyEnvelope(originalLowPcm, attackMs = 1L, releaseMs = 50L)
        val adjustedHighPcm = applyVolume(envelopedHighPcm, 0.5f)
        val adjustedLowPcm = applyVolume(envelopedLowPcm, 0.5f)

        metronomeHighAudioTrack = createStaticAudioTrack(adjustedHighPcm)
        metronomeLowAudioTrack = createStaticAudioTrack(adjustedLowPcm)

        Log.e("DEBUG_SETUP", "Metronome tracks recreated - High state: ${metronomeHighAudioTrack?.state}, Low state: ${metronomeLowAudioTrack?.state}")


        this.trackConfig = trackConfiguration
        this.trackTimeline = TrackTimeline(
            trackConfiguration.progressionConfig,
            trackConfiguration.bpm
        )
        trackTimeline.chordEvents.forEach { chordEvent ->
            val chordUnmixedSamples: List<ShortArray> = ChordTypes.entries[chordEvent.typeIdx].intervals.map { interval ->
                val actualNote = Notes.allNotes.first { it.semitone == (chordEvent.root.semitone + interval) % 12 }
                val actualOctave = chordEvent.octave + (chordEvent.root.semitone + interval) / 12
                noteToSoundId[actualNote to actualOctave] ?: shortArrayOf()
            }

            val mixedChord = mixSamples(chordUnmixedSamples)
            val attackDurationMs = 150L
            val releaseDurationMs = (chordEvent.durationMs * 0.5).toLong()

            val ambientChord = applyEnvelope(mixedChord, attackDurationMs, releaseDurationMs)

            val audioTrack = createStaticAudioTrack(ambientChord)
            chordAudioTracks.add(audioTrack)
        }
    }

//    fun updateTimeSignature(timeSignature: TimeSignature) {
//        val wasPlaying = job?.isActive ?: false
//        stopTrack()
//        setupTrack(trackConfig.copy(timeSignature = timeSignature))
//        if(wasPlaying) playTrack()
//    }
    private fun createStaticAudioTrack(pcmData: ShortArray): AudioTrack {
        val sampleRate = 44100

        Log.e("METRONOME", "sampleRate = $sampleRate")

        val audioTrack = AudioTrack(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build(),
            AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setSampleRate(sampleRate)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build(),
            pcmData.size * 2, // Tamaño del búfer recomendado
            AudioTrack.MODE_STATIC,
            AudioManager.AUDIO_SESSION_ID_GENERATE
        )
        audioTrack.write(pcmData, 0, pcmData.size)

        return audioTrack
    }



    override fun playTrack() {
        job?.cancel()

        Log.d("TrackConfig", "Config enviada a AudioTrack: $trackConfig")

        job = CoroutineScope(Dispatchers.Default).launch {
            // Conteo inicial si está habilitado
            if (trackConfig.metronomeCountIn) {
                val countInBeats = trackConfig.timeSignature.beatsPerMeasure
                for (beat in 0 until countInBeats) {
                    if (!isActive) break

                    // Reproducir el sonido correspondiente
                    val track = if (beat == 0) {
                        metronomeHighAudioTrack
                    } else {
                        metronomeLowAudioTrack
                    }
                    Log.e("DEBUG_COUNTIN", "beat=$beat, track is null? ${track == null}, state=${track?.state}")
                    track?.let {
                        if (it.state == AudioTrack.STATE_INITIALIZED) {
                            it.stop()
                            it.setPlaybackHeadPosition(0)
//                            it.reloadStaticData()
                            Log.i("TrackPlayerImp", "Playing count tick")
                            it.play()
                        }
                    }
                    delay(trackTimeline.beatDurationMs)
                }
            }
            var timelineIterationStartReference = System.currentTimeMillis() + 100 // Añadir un pequeño búfer

            do {
                val startReference = timelineIterationStartReference


                /* Metrónomo */
                if (trackConfig.metronomeEnabled) { // Suponiendo que tienes este flag en TrackConfiguration
                    launch {
                        for (beat in 0 until trackTimeline.totalBeats) {
                            if (!isActive) break
                            val beatTime = startReference + (beat * trackTimeline.beatDurationMs)

                            // Esperar hasta el momento del pulso
                            val delayTime = beatTime - System.currentTimeMillis()
                            if (delayTime > 0) {
                                delay(delayTime)
                            }
                            if (!isActive) break

                            // Reproducir el sonido correspondiente
                            val track = if (beat % trackConfig.timeSignature.beatsPerMeasure == 0) {
                                metronomeHighAudioTrack
                            } else {
                                metronomeLowAudioTrack
                            }

                            Log.e("DEBUG_METRO", "beat=$beat, track is null? ${track == null}, state=${track?.state}")
                            track?.let {
                                if (it.state == AudioTrack.STATE_INITIALIZED) {
                                    it.stop()
                                    it.setPlaybackHeadPosition(0)
//                                    it.reloadStaticData()
                                    Log.i("TrackPlayerImp", "Playing metronome tick")
                                    it.play()
                                }
                            }
                        }
                    }
                }


                trackTimeline.chordEvents.forEachIndexed { index, chordEv ->

                    val eventStart = startReference + chordEv.startTimeMs
                    while (System.currentTimeMillis() < eventStart) {
                        delay(1)
                    }
                    if (!isActive) return@launch

                    var chordAudioTrack: AudioTrack
                    try {
                        chordAudioTrack = chordAudioTracks[index]
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return@launch
                    }
                    if (chordAudioTrack.state != AudioTrack.STATE_INITIALIZED) return@launch

                    chordAudioTrack.stop()
                    chordAudioTrack.setPlaybackHeadPosition(0)
                    chordAudioTrack.play()

                    // Detener acorde
                    launch {
                        delay(chordEv.durationMs)
                        if (isActive) chordAudioTrack.stop()
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

    override fun pauseTrack() {
//        job?.cancel()
//        job = null
//        chordAudioTracks.forEach {
//            if (it.playState == AudioTrack.PLAYSTATE_PLAYING) it.stop()
//        }
        stopTrack()
    }

    override fun stopTrack() {
        Log.d("TrackPlayerImp", "Stopping track")
        job?.cancel()
        job = null
        chordAudioTracks.forEach {
            if (it.playState == AudioTrack.PLAYSTATE_PLAYING) it.stop()
        }

//        metronomeHighAudioTrack?.stop()
//        metronomeLowAudioTrack?.stop()
        if (metronomeHighAudioTrack?.state == AudioTrack.STATE_INITIALIZED &&
            metronomeHighAudioTrack?.playState == AudioTrack.PLAYSTATE_PLAYING
        ) {
            metronomeHighAudioTrack?.stop()
        }

        if (metronomeLowAudioTrack?.state == AudioTrack.STATE_INITIALIZED &&
            metronomeLowAudioTrack?.playState == AudioTrack.PLAYSTATE_PLAYING
        ) {
            metronomeLowAudioTrack?.stop()
        }
    }
    override fun release() {
        job?.cancel()
        job = null
        chordAudioTracks.forEach { it.release() }
        chordAudioTracks.clear()
        metronomeHighAudioTrack?.release()
        metronomeLowAudioTrack?.release()
    }


}