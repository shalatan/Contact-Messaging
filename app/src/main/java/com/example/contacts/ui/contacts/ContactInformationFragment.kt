package com.example.contacts.ui.contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contacts.databinding.FragmentContactInformationBinding
import com.example.contacts.model.Contact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactInformationFragment : Fragment() {

    private var _binding: FragmentContactInformationBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedContact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedContact =
            ContactInformationFragmentArgs.fromBundle(requireArguments()).selectedContact
        Log.e("ABCD", "$selectedContact")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactInformationBinding.inflate(inflater)

        val fullName = "${selectedContact.firstName} ${selectedContact.lastName}"
        binding.contactName.text = fullName

        val phoneNumber = selectedContact.contactNumber
        binding.contactNumber.text = phoneNumber

        binding.sendMessageButton.setOnClickListener {
            findNavController().navigate(
                ContactInformationFragmentDirections.actionContactInformationFragmentToNewMessageFragment(
                    selectedContact
                )
            )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}