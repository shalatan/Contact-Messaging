package com.example.contacts.ui.newmessage

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

    private lateinit var messageEditText: TextInputLayout
    private lateinit var messageTextView: TextView
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

        sendButton = binding.send
        messageTextView = binding.messageTextView
        messageEditText = binding.messageEditText

        otp = (100000..999999).random()
        initialMessage = "Hey, Your OTP is : $otp\n"
        messageTextView.text = initialMessage

        messageEditText.editText?.doOnTextChanged { text, _, _, _ ->
            sendButton.isEnabled = !text.isNullOrEmpty()
            val messageText = "$initialMessage $text"
            messageTextView.text = messageText
        }

        binding.send.setOnClickListener {
            sendMessage()
        }

        viewModel.messageResponse.observe(viewLifecycleOwner) {
            if (it?.status == "queued") {
                Toast.makeText(context, "Message Sent Successfully", Toast.LENGTH_SHORT).show()
                val savedMessage = SavedMessage(
                    message_receiver = "${selectedContact.firstName} ${selectedContact.lastName}",
                    message_otp = otp.toString(),
                    message_date = it.date_created
                )
                Log.e("ABED", savedMessage.toString())
                viewModel.insertMessageToDatabase(savedMessage)
                sendButton.isEnabled = true
                viewModel.clearResponse()
            } else if (it?.message != null) {
                Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * send the text as message to selectedContact
     */
    private fun sendMessage() {
        if (hasNetwork(requireActivity()) == true) {
            //device has active internet connection
            sendButton.isEnabled = false
            finalMessage = messageTextView.text.toString()
            val from = Constants.TWILIO_CONTACT_NUMBER
            val to = selectedContact.contactNumber

            val base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (Constants.TWILIO_SID + ":" + Constants.TWILIO_TOKEN).toByteArray(),
                Base64.NO_WRAP
            )

            val data: MutableMap<String, String> = HashMap()
            data["From"] = from
            data["To"] = to
            data["Body"] = finalMessage

            lifecycleScope.launch {
                viewModel.send(
                    sig = base64EncodedCredentials,
                    data = data
                )
            }
        } else {
            //device is not connected to internet
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * check if device has active network connection
     */
    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}