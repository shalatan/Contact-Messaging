package com.example.contacts


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.database.SavedMessage
import com.example.contacts.ui.messages.MessageAdapter

//bind recycler view adapter for messages
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<SavedMessage>?) {
    val adapter = recyclerView.adapter as MessageAdapter
    adapter.submitList(data)
}
