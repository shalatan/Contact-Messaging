package com.example.contacts.ui.contacts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.model.MessageResponse
import com.example.contacts.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class NewMessageViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    private val _messageResponse = MutableLiveData<MessageResponse>()
    val messageResponse: LiveData<MessageResponse>
        get() = _messageResponse

    suspend fun send(sig: String, data: Map<String, String>) {
        val responseDeferred = repository.sendMessage(sig, data)
        try {
            _messageResponse.value = responseDeferred.await()
            Log.e("ABCD S", _messageResponse.value.toString())
        } catch (exception: SocketTimeoutException) {
            Log.e("ABCD E1", exception.toString())
        } catch (t: Throwable) {
            Log.e("ABCD E2", t.message.toString())
        }
    }

}