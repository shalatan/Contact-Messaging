package com.example.contacts.ui.newmessage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.Repository
import com.example.contacts.database.SavedMessage
import com.example.contacts.model.MessageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class NewMessageViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {

    private val _messageResponse = MutableLiveData<MessageResponse?>()
    val messageResponse: LiveData<MessageResponse?>
        get() = _messageResponse

    suspend fun send(sig: String, data: Map<String, String>) {
        val responseDeferred = repository.sendMessageAsync(sig, data)
        try {
            _messageResponse.value = responseDeferred.await()
            Log.e("Response", _messageResponse.value.toString())
        } catch (exception: SocketTimeoutException) {
            Log.e("Error Getting Response", exception.toString())
        } catch (t: Throwable) {
            Log.e("Error Getting Response", t.toString())
        }
    }

    fun clearResponse() {
        _messageResponse.value = null
    }

    fun insertMessageToDatabase(savedMessage: SavedMessage) {
        viewModelScope.launch {
            repository.insertMessage(savedMessage)
        }
    }
}