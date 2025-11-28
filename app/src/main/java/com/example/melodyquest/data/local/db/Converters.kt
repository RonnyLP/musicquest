package com.example.melodyquest.data.local.db

import androidx.room.TypeConverter
import com.example.melodyquest.domain.model.TrackConfiguration
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converters {
    @TypeConverter
    fun fromTrackConfiguration(value: TrackConfiguration): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toTrackConfiguration(value: String): TrackConfiguration {
        return Json.decodeFromString(value)
    }
}