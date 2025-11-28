package com.example.melodyquest.data.local.dao

import androidx.room.*
import com.example.melodyquest.data.local.entity.SavedAccount

@Dao
interface SavedAccountDao {

    @Query("SELECT * FROM saved_accounts")
    suspend fun getAll(): List<SavedAccount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: SavedAccount)

    @Delete
    suspend fun delete(account: SavedAccount)
}