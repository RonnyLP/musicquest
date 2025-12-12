package com.example.melodyquest.data.repository

import com.example.melodyquest.data.local.entity.Track
import com.example.melodyquest.data.remote.TrackLibraryRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//class LibraryRepository(
//    private val db: FirebaseFirestore
//) {
//
//    fun getLibraryTracks(): Flow<List<Track>> = callbackFlow {
//        val listener = db.collection("library")
//            .addSnapshotListener { value, error ->
//                if (error != null) {
//                    close(error)
//                } else {
//                    trySend(value!!.toObjects(Track::class.java))
//                }
//            }
//        awaitClose { listener.remove() }
//    }
//
//    suspend fun getTrackById(id: String): Track? {
//        return db.collection("library")
//            .document(id)
//            .get()
//            .await()
//            .toObject(Track::class.java)
//    }
//}

class LibraryRepository @Inject constructor(
    private val remote: TrackLibraryRemoteDataSource
) {
    suspend fun getLibraryTracks() = remote.getLibraryTracks()
    suspend fun updateTrack(track: Track) = remote.updateLibraryTrack(track)
    suspend fun addTrack(track: Track) = remote.addLibraryTrack(track)
    suspend fun getTrackById(id: String) = remote.getLibraryTracks().find { it.id == id }

}