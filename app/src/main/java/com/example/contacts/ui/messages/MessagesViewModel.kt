package com.example.contacts.ui.messages

import androidx.lifecycle.ViewModel
import com.example.contacts.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(private val repository: DatabaseRepository) :
    ViewModel() {

    val messages = repository.getAllMessages()

}