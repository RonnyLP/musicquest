package com.example.melodyquest.data.repository

import com.example.melodyquest.data.local.dao.SavedAccountDao
import com.example.melodyquest.data.local.entity.SavedAccount
import javax.inject.Inject

class SavedAccountsRepository @Inject constructor(
    private val dao: SavedAccountDao
) {

    suspend fun getSavedAccounts(): List<String> =
        dao.getAll().map { it.email }

    suspend fun addAccount(email: String) =
        dao.insert(SavedAccount(email))

    suspend fun removeAccount(email: String) =
        dao.delete(SavedAccount(email))
}
