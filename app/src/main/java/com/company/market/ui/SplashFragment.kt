package com.company.market.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.company.market.MarketApplication
import com.company.market.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSplashBinding.inflate(inflater, container, false).root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenResumed {
            delay(1000)
            if ((activity?.application as MarketApplication).appContainer.currentUserToken == null) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMarketFragment())
            }

        }

    }
}