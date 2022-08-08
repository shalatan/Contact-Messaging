package com.example.contacts.network

import com.example.contacts.model.MessageResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface RetrofitService {

    @FormUrlEncoded
    @POST("Accounts/{ACCOUNT_SID}/SMS/Messages.json")
    fun sendMessage(
        @Path("ACCOUNT_SID") accountSid: String,
        @Header("Authorization") signature: String,
        @FieldMap metadata: Map<String, String>
    ): Deferred<MessageResponse>

}