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
import com.company.market.R
import com.company.market.databinding.FragmentCartBinding
import com.company.market.ui.market.MarketFragment
import com.company.market.ui.market.MarketFragmentDirections


class CartFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this@CartFragment,
            CartVMFactory(
                (requireActivity().application as MarketApplication).appContainer.orderDao,
                (requireActivity().application as MarketApplication).appContainer.remoteApi,
                (requireActivity().application as MarketApplication).appContainer.productDao
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
        val user = (requireActivity().application as MarketApplication).appContainer.currentUserToken

        viewModel.apply {
            ordersInCart.observe(viewLifecycleOwner) {orders ->
                cartAdapter.submitList(orders.toMutableList())

                totalSum.observe(viewLifecycleOwner) { sum: Double? ->
                    sum?.let {
                        binding.apply {
                            totalAmount.text = String.format("₹ %.2f", sum)
                            orderButton.text = resources.getString(R.string.place_order)
                            orderButton.setOnClickListener {
                                user?.let { it1 ->
                                viewModel.orderProducts(it1, sum, orders)
                                }
                            }
                        }
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
                setOnClickListener{
                    findNavController().navigate(CartFragmentDirections.actionCartFragmentToMarketFragment())
                }
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