package com.example.contacts.network

import com.example.contacts.model.MessageResponse
import com.example.contacts.utils.Constants
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val retrofitService: RetrofitService) {

    /**
     * send the message via retrofit service
     * @param signature - Base64EncodedCredentials
     * @param data - HashMap of 'to', 'from', 'body'
     */
    fun sendMessageAsync(signature: String, data: Map<String, String>): Deferred<MessageResponse> {
        return retrofitService.sendMessage(
            accountSid = Constants.TWILIO_SID,
            signature = signature,
            metadata = data
        )
    }
}