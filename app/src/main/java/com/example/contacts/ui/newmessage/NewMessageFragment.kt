package com.example.contacts.ui.newmessage

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.contacts.databinding.FragmentNewMessageBinding
import com.example.contacts.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewMessageFragment : Fragment() {

    private var _binding: FragmentNewMessageBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactNumber: String

    val viewModel: NewMessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactNumber =
            NewMessageFragmentArgs.fromBundle(requireArguments()).phoneNumber
        Log.e("ABCD", contactNumber)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewMessageBinding.inflate(inflater)

        binding.send.setOnClickListener {
            val body = "Hello test"
            val from = "+13304463624"
            val to = "+917599185055"

            val base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (Constants.TWILIO_SID + ":" + Constants.TWILIO_TOKEN).toByteArray(), Base64.NO_WRAP
            )

            val data: MutableMap<String, String> = HashMap()
            data["From"] = from
            data["To"] = to
            data["Body"] = body

            lifecycleScope.launch {
                viewModel.send(
                    sig = base64EncodedCredentials,
                    data = data
                )
            }

            viewModel.messageResponse.observe(viewLifecycleOwner) {
                Log.e("ABCD F", it.toString())
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}