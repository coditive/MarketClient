package com.company.market.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.company.market.MarketApplication
import com.company.market.data.Address
import com.company.market.data.UserSignUp
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
            buttonRegister.setOnClickListener {
                val (first , last) = editTextName.text.split(" ")
                val email = editTextEmail.text.toString()
                val phone =  editTextPhone.text.toString()
                val loc = editTextAddressLoc.text.toString()
                val pin = editTextAddressPincode.text.toString()

                when {
                    last.isEmpty() -> editTextName.error = "Enter Your Full Name"
                    email.isEmpty() -> editTextEmail.error = "Email is required"
                    phone.isEmpty() -> editTextPhone.error = "Phone Number is required"
                    loc.isEmpty() -> editTextAddressLoc.error = "Address is required"
                    pin.isEmpty() -> editTextAddressPincode.error = "Pincode is required"
                    else ->
                        viewModel.attemptRegister(UserSignUp(first, last, phone.toInt(), email,
                            Address(loc, "home", pin.toInt())))
                }
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToMarketFragment())
            }
        }
        return binding.root
    }
}