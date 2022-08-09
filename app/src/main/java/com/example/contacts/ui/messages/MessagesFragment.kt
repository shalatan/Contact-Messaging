package com.example.contacts.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.R
import com.example.contacts.databinding.FragmentMessagesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MessagesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater)

        //divider for recyclerView
        val decoration =
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.recycler_view_divider)?.let {
            decoration.setDrawable(it)
        }

        val messageAdapter = MessageAdapter()
        val recyclerView = binding.messagesRecyclerView
        recyclerView.apply {
            adapter = messageAdapter
            addItemDecoration(decoration)
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                recyclerView.visibility = View.GONE
                binding.emptyTextView.visibility = View.VISIBLE
            }else{
                recyclerView.visibility = View.VISIBLE
                binding.emptyTextView.visibility = View.GONE
                messageAdapter.submitList(it)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}