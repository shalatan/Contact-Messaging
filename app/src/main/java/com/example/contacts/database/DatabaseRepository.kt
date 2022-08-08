package com.example.contacts.database

import androidx.lifecycle.LiveData
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: MessagesDAO) {

    suspend fun insertMessage(savedMessage: SavedMessage) {
        dao.insert(savedMessage)
    }

    fun getAllMessages(): LiveData<List<SavedMessage>> {
        return dao.getAllMessages()
    }
}


