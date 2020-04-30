package com.company.market.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.market.MarketApplication
import com.company.market.databinding.FragmentCartBinding
import com.company.market.ui.market.MarketVM
import com.company.market.ui.market.MarketVMFactory

class CartFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this@CartFragment,
            CartVMFactory(
                (requireActivity().application as MarketApplication).appContainer.productDao,
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

        val cartAdapter = CartAdapter(ClickHandler(), viewModel.productsInCart.value, viewModel.orders.value)

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