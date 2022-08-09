package com.example.contacts

import androidx.lifecycle.LiveData
import com.example.contacts.database.MessagesDAO
import com.example.contacts.database.SavedMessage
import com.example.contacts.model.MessageResponse
import com.example.contacts.network.RetrofitService
import com.example.contacts.utils.Constants
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class Repository @Inject constructor(
    private val dao: MessagesDAO,
    private val retrofitService: RetrofitService
) {

    /**
     * insert new message into db
     * @param savedMessage - new message
     */
    suspend fun insertMessage(savedMessage: SavedMessage) {
        dao.insert(savedMessage)
    }

    /**
     * get all messages from db sorted by date
     */
    fun getAllMessages(): LiveData<List<SavedMessage>> {
        return dao.getAllMessages()
    }

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


