package com.company.market.ui.login

import android.content.Context
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
import com.company.market.R
import com.company.market.databinding.FragmentLoginBinding
import com.company.market.utils.SHARED_PREFS_KEY
import com.company.market.utils.TOKEN_KEY

class LoginFragment : Fragment() {
    private val viewModel: LoginVM by lazy {
        ViewModelProvider(this@LoginFragment).get(
            LoginVM::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

//      //TODO() : put below code when defining the flow of ui.
//        val prefs = requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
//        val token = prefs.getString(TOKEN_KEY, null)
//        if(token != null) {
//            findNavController().navigate(R.id.marketFragment)
//        }
//     //

        viewModel.token.observe(viewLifecycleOwner) {
            if (it != null)
                requireContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
                    ?.edit()?.apply {
                        putString(TOKEN_KEY, it)
                        apply()
                    }

        }
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                LoginState.NOT_LOGGED_IN -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Please enter login details",
                        Toast.LENGTH_LONG
                    ).show()
                }

                LoginState.IN_PROGRESS -> binding.progressBar.visibility = View.VISIBLE
                LoginState.LOGIN_ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.editTextUsername.error = getString(R.string.user_name_error)
                    binding.editTextPassword.error = getString(R.string.pass_error)
                    Toast.makeText(
                        requireContext(),
                        "Please enter correct login details",
                        Toast.LENGTH_LONG
                    ).show()
                }

                LoginState.LOGGED_IN -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Logged in!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.marketFragment)
                }

                LoginState.CREATE_USER -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "User Created!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }

        binding.button.setOnClickListener {
            val userName: String = binding.editTextUsername.editText?.text.toString()
            val password: String = binding.editTextPassword.editText?.text.toString()
            Log.d("Login Details", "Username : $userName, pass : $password")
            viewModel.attemptLogin(userName, password)
        }

        return binding.root
    }
}