package com.company.market.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.market.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCartBinding.inflate(inflater, container, false)
        val cartAdapter = CartAdapter(ClickHandler())
//        viewModel.cartItemList.observe(viewLifecycleOwner) {
//            cartAdapter.submitList(it.toMutableList())
//            val sum = it.sumBy { pair -> pair.first.price * pair.second }
//            binding.orderButton.apply {
//                if (sum != 0) {
//                    text = resources.getString(R.string.pay_amount, sum)
//                    isEnabled = true
//
//                } else {
//                    isEnabled = false
//                    text = resources.getString(R.string.add_item_in_cart)
//                }
//
//            }
//        }

        binding.apply {
            toolbar.setupWithNavController(findNavController())
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = cartAdapter
            }
            orderButton.apply {
                setOnClickListener {
//                    if (viewModel.cartItemList.value?.sumBy { pair -> pair.first.price * pair.second } ?: 0 != 0) {
//                        Log.i(TAG, "not yet implemented")
//                    }
                }
            }
        }
        return binding.root
    }

    inner class ClickHandler {
        fun add(index: Int) = Unit

        //            viewModel.increaseQuantity(index)
        fun remove(index: Int) = Unit
//    viewModel.decreaseQuantity(index)
    }

    companion object {
        private const val TAG = "CartFragment"
    }
}