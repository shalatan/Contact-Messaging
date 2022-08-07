package com.example.contacts.database

import androidx.room.*

@Dao
interface MessagesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(savedMessage: SavedMessage)

}