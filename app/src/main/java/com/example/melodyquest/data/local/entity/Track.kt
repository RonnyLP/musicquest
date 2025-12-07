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
    @PrimaryKey var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "data") var data: TrackConfiguration,
    @ColumnInfo(name = "ownerEmail") val ownerEmail: String
)