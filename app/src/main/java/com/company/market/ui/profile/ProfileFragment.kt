package com.company.market.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.company.market.MarketApplication
import com.company.market.data.UserProfile
import com.company.market.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(
            this@ProfileFragment, ProfileVMFactory(
                (requireActivity().application as MarketApplication).appContainer.remoteApi,
                (requireActivity().application as MarketApplication).appContainer.userProfileDao,
                (requireActivity().application as MarketApplication).appContainer.currentUserToken
            )
        ).get(ProfileVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.apply {
            profileModel.observe(viewLifecycleOwner) { model: UserProfile? ->
                Log.i(TAG, "new model : $model")
                model?.let {
                    binding.apply {
                        textViewName.text =
                            String.format("%s %s", model.first_name, model.last_name)
                        textViewAddress.text = model.address.toString()
                        textViewEmail.text = model.email
                        textViewPhone.text = model.phone.toString()
                    }
                }
            }

            loadingState.observe(viewLifecycleOwner) { loading ->
                binding.progressCircular.visibility = if (loading) View.VISIBLE else View.GONE
            }
        }
        binding.apply {
            refreshButton.setOnClickListener { viewModel.refreshProfile() }
            buttonMyOrders.setOnClickListener {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToOrdersFragment()
                )
            }
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

    companion object {
        private const val TAG = "ProfileFragment"
    }
}