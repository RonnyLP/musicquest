package com.example.melodyquest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_accounts")
data class SavedAccount(
    @PrimaryKey val email: String
)
