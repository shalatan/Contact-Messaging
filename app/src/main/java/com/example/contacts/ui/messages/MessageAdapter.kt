package com.example.contacts.ui.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.database.SavedMessage
import com.example.contacts.databinding.ItemMessageBinding

class MessageAdapter() :
    ListAdapter<SavedMessage, MessageAdapter.MessageViewHolder>(DiffCallBack) {

    class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(savedMessage: SavedMessage) {
            binding.message = savedMessage
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<SavedMessage>() {
        override fun areItemsTheSame(oldItem: SavedMessage, newItem: SavedMessage): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: SavedMessage, newItem: SavedMessage): Boolean {
            return oldItem.message_id == newItem.message_id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
        return MessageViewHolder(ItemMessageBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }
}