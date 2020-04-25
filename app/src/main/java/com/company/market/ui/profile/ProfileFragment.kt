package com.company.market.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.company.market.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.apply {
            editFab.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Edit profile yet to be implemented",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }
}