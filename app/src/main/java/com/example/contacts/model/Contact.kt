package com.example.contacts.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val firstName: String,
    val lastName: String,
    val contactNumber: String
) : Parcelable