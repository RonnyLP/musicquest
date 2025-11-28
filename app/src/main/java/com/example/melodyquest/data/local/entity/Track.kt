package com.example.melodyquest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.melodyquest.domain.model.TrackConfiguration

@Entity
data class Track(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "data") val data: TrackConfiguration,
)