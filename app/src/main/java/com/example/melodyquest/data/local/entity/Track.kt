package com.example.melodyquest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.melodyquest.domain.model.TrackConfiguration

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SavedAccount::class,
            parentColumns = ["email"],
            childColumns = ["ownerEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ],
//    indices = [Index("ownerEmail")]
)
data class Track(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "data") val data: TrackConfiguration,
    @ColumnInfo(name = "ownerEmail") val ownerEmail: String
)