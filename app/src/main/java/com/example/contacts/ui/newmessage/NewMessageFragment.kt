package com.example.contacts.ui.newmessage

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.contacts.database.SavedMessage
import com.example.contacts.databinding.FragmentNewMessageBinding
import com.example.contacts.model.Contact
import com.example.contacts.utils.Constants
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewMessageFragment : Fragment() {

    private var _binding: FragmentNewMessageBinding? = null
    private val binding get() = _binding!!

    private lateinit var messageTextField: TextInputLayout
    private lateinit var sendButton: Button
    private lateinit var selectedContact: Contact
    private lateinit var initialMessage: String
    private lateinit var finalMessage: String
    private var otp: Int = 0

    private val viewModel: NewMessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedContact = NewMessageFragmentArgs.fromBundle(requireArguments()).selectedContact
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewMessageBinding.inflate(inflater)

        otp = (100000..999999).random()
        initialMessage = "Hey, Your OTP is : $otp"

        sendButton = binding.send
        messageTextField = binding.messageTextField
        messageTextField.editText?.setText(initialMessage)
        messageTextField.editText?.doOnTextChanged { text, _, _, _ ->
            sendButton.isEnabled = !text.isNullOrEmpty()
        }

        binding.send.setOnClickListener {
            finalMessage = messageTextField.editText?.text.toString()
            val from = Constants.TWILIO_CONTACT_NUMBER
            val to = selectedContact.contactNumber

            val base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (Constants.TWILIO_SID + ":" + Constants.TWILIO_TOKEN).toByteArray(), Base64.NO_WRAP
            )

            val data: MutableMap<String, String> = HashMap()
            data["From"] = from
            data["To"] = to
            data["Body"] = finalMessage

            Log.e("ABCD MAP : ", data.toString())

            lifecycleScope.launch {
                viewModel.send(
                    sig = base64EncodedCredentials,
                    data = data
                )
            }
        }

        viewModel.messageResponse.observe(viewLifecycleOwner) {
            if (it.status == "queued") {
                val savedMessage = SavedMessage(
                    message_receiver = "${selectedContact.firstName} ${selectedContact.lastName}",
                    message_otp = otp.toString(),
                    message_date = it.date_created
                )
                viewModel.insertMessageToDatabase(savedMessage)
            } else {
                Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}