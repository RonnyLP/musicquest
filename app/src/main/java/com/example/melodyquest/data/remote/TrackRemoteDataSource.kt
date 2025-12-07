package com.example.melodyquest.data.remote

import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.domain.model.TrackConfiguration
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TrackRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun tracksCollection(email: String) =
        firestore.collection("users")
            .document(email)
            .collection("tracks")

    suspend fun getTracks(email: String): List<Track> {
        val snap = tracksCollection(email).get().await()

        return snap.documents.mapNotNull { doc ->

            val name = doc.getString("name") ?: return@mapNotNull null
            val dataString = doc.getString("data") ?: return@mapNotNull null

            val config = Json.decodeFromString<TrackConfiguration>(dataString)

            Track(
                id = doc.id,
                name = name,
                data = config,
                ownerEmail = email
            )
        }
    }


    suspend fun addTrack(email: String, track: Track) {
        val newRef = tracksCollection(email).document()
//        val id = newRef.id
//        track.id = newRef.id

        newRef.set(
            mapOf(
                "name" to track.name,
                "data" to Json.encodeToString(track.data)
            )
        ).await()
    }

    suspend fun deleteTrack(email: String, trackId: String) {
        tracksCollection(email).document(trackId).delete().await()
    }

    suspend fun updateTrack(email: String, track: Track) {
        println("Updating track: ${track.id}")
        tracksCollection(email)
            .document(track.id)
            .set(
            mapOf(
                "name" to track.name,
                "data" to Json.encodeToString(track.data)
            )
        ).await()
    }

}
