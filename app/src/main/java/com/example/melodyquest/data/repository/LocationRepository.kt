package com.example.melodyquest.data.repository

import com.example.melodyquest.domain.model.UserLocation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) {


    private val locationRef = database.getReference("user_locations")

    suspend fun saveLocation(lat: Double, lon: Double) {
        val uid = auth.currentUser?.uid ?: return
        val location = UserLocation(lat, lon)
        locationRef.child(uid).setValue(location).await()
    }

    suspend fun getLocation(uid: String): UserLocation? {
        val snapshot = locationRef.child(uid).get().await()
        return snapshot.getValue(UserLocation::class.java)
    }



    fun observeLocation(
        uid: String,
        onChange: (UserLocation?) -> Unit
    ) {
        locationRef.child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onChange(snapshot.getValue(UserLocation::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejo opcional
                }
            })
    }
}