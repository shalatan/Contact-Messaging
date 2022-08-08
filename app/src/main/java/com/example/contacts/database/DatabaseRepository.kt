package com.example.contacts.database

import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: MessagesDAO) {

    suspend fun insertMessage(savedMessage: SavedMessage) {
        dao.insert(savedMessage)
    }
}


