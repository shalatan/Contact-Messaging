package com.example.contacts.database

import androidx.lifecycle.LiveData
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: MessagesDAO) {

    /**
     * insert new message into db
     * @param savedMessage - new message
     */
    suspend fun insertMessage(savedMessage: SavedMessage) {
        dao.insert(savedMessage)
    }

    /**
     * get all messages from db sorted by date
     */
    fun getAllMessages(): LiveData<List<SavedMessage>> {
        return dao.getAllMessages()
    }
}


