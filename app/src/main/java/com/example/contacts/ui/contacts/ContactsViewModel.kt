package com.example.contacts.ui.contacts

import androidx.lifecycle.ViewModel
import com.example.contacts.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repository: DatabaseRepository) :
    ViewModel() {

}