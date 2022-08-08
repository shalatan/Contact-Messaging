package com.example.contacts.model

data class MessageResponse(
    val body: String,
    val date_created: String,
    val message:String?,
    val status: String,
)