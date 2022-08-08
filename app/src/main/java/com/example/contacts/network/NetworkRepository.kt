package com.example.contacts.network

import com.example.contacts.model.MessageResponse
import com.example.contacts.utils.Constants
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val retrofitService: RetrofitService) {

    fun sendMessageAsync(signature: String, data: Map<String, String>): Deferred<MessageResponse> {
        return retrofitService.sendMessage(
            accountSid = Constants.TWILIO_SID,
            signature = signature,
            metadata = data
        )
    }
}