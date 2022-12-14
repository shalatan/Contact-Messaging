package com.example.contacts.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.contacts.databinding.FragmentContactsBinding
import com.example.contacts.model.Contact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    val viewModel: ContactsViewModel by viewModels()

    //fake contacts data
    private val contacts: List<Contact> = listOf(
        Contact("Shashank", "Singh", "+917599185055"),
        Contact("Kisan", "Network", "+919810153260"),
        Contact("Dummmy 1", "Invalid Number", "+91123456789"),
        Contact("Dummmy 2", "Invalid Number", "+91123456789"),
        Contact("Dummmy 3", "Invalid Number", "+91123456789"),
        Contact("Dummmy 4", "Invalid Number", "+91123456789"),
        Contact("Dummmy 5", "Invalid Number", "+91123456789")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater)

        val contactAdapter = ContactAdapter(ContactAdapter.OnClickListener {
            findNavController().navigate(
                ContactsFragmentDirections.actionNavigationContactsToContactInformationFragment(
                    it
                )
            )
        })

        val recyclerView = binding.contactRecyclerView
        recyclerView.adapter = contactAdapter
        contactAdapter.submitList(contacts)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}