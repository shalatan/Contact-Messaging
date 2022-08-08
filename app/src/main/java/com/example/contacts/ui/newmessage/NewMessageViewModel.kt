package com.example.contacts.ui.newmessage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.database.DatabaseRepository
import com.example.contacts.model.MessageResponse
import com.example.contacts.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class NewMessageViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) :
    ViewModel() {

    private val _messageResponse = MutableLiveData<MessageResponse>()
    val messageResponse: LiveData<MessageResponse>
        get() = _messageResponse

    suspend fun send(sig: String, data: Map<String, String>) {
        val responseDeferred = networkRepository.sendMessageAsync(sig, data)
        try {
            _messageResponse.value = responseDeferred.await()
            Log.e("ABCD R", _messageResponse.value.toString())
        } catch (exception: SocketTimeoutException) {
            Log.e("ABCD E", exception.toString())
        } catch (t: Throwable) {
            Log.e("ABCD T", t.toString())
        }
    }
}