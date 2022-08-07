package com.example.contacts.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedMessage::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun logDao(): MessagesDAO
}