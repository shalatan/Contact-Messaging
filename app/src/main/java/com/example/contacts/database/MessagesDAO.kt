package com.example.contacts.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MessagesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(savedMessage: SavedMessage)

    @Query("SELECT * FROM saved_messages_table ORDER BY message_date")
    fun getAllMessages(): LiveData<List<SavedMessage>>
}