package com.example.melodyquest.data.remote

import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.domain.model.TrackConfiguration
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TrackLibraryRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private fun libraryCollection() =
        firestore.collection("library")

    suspend fun getLibraryTracks(): List<Track> {
        val snap = libraryCollection().get().await()

        return snap.documents.mapNotNull { doc ->

            val name = doc.getString("name") ?: return@mapNotNull null
            val dataString = doc.getString("data") ?: return@mapNotNull null

            val config = Json.decodeFromString<TrackConfiguration>(dataString)

            Track(
                id = doc.id,
                name = name,
                data = config,
                ownerEmail = ""
            )
        }
    }

    suspend fun addLibraryTrack(track: Track) {
        val newRef = libraryCollection().document(track.id.ifEmpty { null } ?: libraryCollection().document().id)

        newRef.set(
            mapOf(
                "name" to track.name,
                "data" to Json.encodeToString(track.data)
            )
        ).await()
    }

    suspend fun deleteLibraryTrack(trackId: String) {
        libraryCollection().document(trackId).delete().await()
    }

    suspend fun updateLibraryTrack(track: Track) {
        libraryCollection()
            .document(track.id)
            .set(
                mapOf(
                    "name" to track.name,
                    "data" to Json.encodeToString(track.data)
                )
            ).await()
    }
}
