package com.example.contacts

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("otpText")
fun bindOtpText(textView: TextView, otp: String) {
    val finalString = "OTP : $otp"
    textView.text = finalString
}

@BindingAdapter("dateText")
fun bindDateText(textView: TextView, date: String) {
    val finalString = date.substring(0,date.indexOf("+"))
    textView.text = finalString
}


