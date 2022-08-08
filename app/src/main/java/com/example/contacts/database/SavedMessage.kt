package com.example.contacts.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * data class to save messages in room db
 */
@Entity(tableName = "saved_messages_table")
data class SavedMessage(
    @PrimaryKey(autoGenerate = true)
    var message_id: Int = 0,
    var message_otp: String,
    var message_receiver: String,
    var message_date: String
)
