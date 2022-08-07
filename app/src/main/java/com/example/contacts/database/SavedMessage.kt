package com.example.contacts.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_messages_table")
data class SavedMessage(
    @PrimaryKey
    var Id: Int = -1,
    var movieTitle: String?,
)
