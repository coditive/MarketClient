package com.company.market.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.company.market.MarketApplication
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
                viewModel.attemptRegister()
            }
        }
        return binding.root
    }
}