package com.example.contacts.di

import android.content.Context
import androidx.room.Room
import com.example.contacts.database.AppDatabase
import com.example.contacts.database.MessagesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MessagesDAO {
        return appDatabase.logDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "messages.db"
        ).build()
    }
}