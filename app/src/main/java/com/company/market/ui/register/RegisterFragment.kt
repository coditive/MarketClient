package com.company.market.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.company.market.MarketApplication
import androidx.lifecycle.observe
import com.company.market.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(
            this, RegisterVMFactory(
                (requireContext().applicationContext as MarketApplication).appContainer.remoteApi
            )
        ).get(RegisterVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel.loading.observe(viewLifecycleOwner){
                if(it){
                    progressBar.visibility = View.VISIBLE
                } else{
                    progressBar.visibility = View.GONE
                }
            }

            viewModel.status.observe(viewLifecycleOwner) {
                when(it){
                    RegistrationState.NOT_REGISTERED ->
                        Toast.makeText(requireActivity(),
                            "Enter Your Details to Register",
                            Toast.LENGTH_SHORT).show()
                    RegistrationState.NAME_ERROR -> editTextName.error = "Enter Your Full Name"
                    RegistrationState.EMAIL_ERROR -> editTextEmail.error = "Enter Your correct email"
                    RegistrationState.PHONE_ERROR -> editTextPhone.error = "Enter your correct phone number"
                    RegistrationState.PINCODE_ERROR -> editTextAddressPincode.error = "Enter your correct pincode"
                    RegistrationState.ADDRESS_ERROR -> editTextAddressLoc.error = "Enter your address"
                    RegistrationState.REGISTERED ->
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToMarketFragment())
                }
            }

            buttonRegister.setOnClickListener {
                val name = editTextName.text.toString()
                val email = editTextEmail.text.toString()
                val phone =  editTextPhone.text.toString()
                val loc = editTextAddressLoc.text.toString()
                val pin = editTextAddressPincode.text.toString()
                viewModel.attemptRegister(name, email, phone, loc, pin)
            }
        }
        return binding.root
    }
}