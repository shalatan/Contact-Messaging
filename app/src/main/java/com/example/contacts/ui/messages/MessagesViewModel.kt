package com.example.contacts.ui.messages

import androidx.lifecycle.ViewModel
import com.example.contacts.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(repository: Repository) :
    ViewModel() {

    val messages = repository.getAllMessages()

}