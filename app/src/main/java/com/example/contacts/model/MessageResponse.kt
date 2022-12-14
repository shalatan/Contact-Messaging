package com.example.contacts.model

/**
 * data class to handle twilio sms api response
 */
data class MessageResponse(
    val account_sid: String,
    val body: String,
    val date_created: String,
    val message: String?,
    val from: String,
    val status: String,
    val to: String,
)