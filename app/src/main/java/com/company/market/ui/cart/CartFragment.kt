package com.company.market.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.market.MarketApplication
import com.company.market.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this@CartFragment,
            CartVMFactory(
                (requireActivity().application as MarketApplication).appContainer.orderDao
            )
        ).get(CartVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCartBinding.inflate(inflater, container, false)
        val cartAdapter = CartAdapter(ClickHandler())
        viewModel.apply {
            ordersInCart.observe(viewLifecycleOwner) {
                cartAdapter.submitList(it.toMutableList())
            }
            totalSum.observe(viewLifecycleOwner) { sum: Int? ->
                sum?.let {
                    binding.apply {
                        totalAmount.text = String.format("₹ %d", sum)
                        orderButton.text = String.format("Pay ₹ %d", sum)
                    }
                }
            }
        }
        binding.apply {
            toolbar.setupWithNavController(findNavController())
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = cartAdapter
            }
            orderButton.apply {
            }
        }

        return binding.root
    }

    inner class ClickHandler {
        fun add(index: Int) {
            viewModel.increaseQuantity(index)
        }

        fun remove(index: Int) {
            viewModel.decreaseOrder(index)
        }
    }

    companion object {
        private const val TAG = "CartFragment"
    }
}