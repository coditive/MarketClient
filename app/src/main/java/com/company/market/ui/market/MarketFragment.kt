package com.company.market.ui.market

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.market.MarketApplication
import com.company.market.R
import com.company.market.databinding.FragmentMarketBinding

class MarketFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this@MarketFragment,
            MarketVMFactory(
                (requireActivity().application as MarketApplication).appContainer.remoteApi,
                (requireActivity().application as MarketApplication).appContainer.productDao
            )
        ).get(MarketVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMarketBinding.inflate(inflater, container, false)
        val productAdapter = ProductAdapter(ClickHandler())
        viewModel.apply {
            loadingState.observe(viewLifecycleOwner) { loading ->
                binding.swipeToRefreshView.isRefreshing = loading
            }
            productList.observe(viewLifecycleOwner) { productAdapter.submitList(it) }
        }
        binding.apply {
            toolbar.apply {
                setupWithNavController(findNavController())
                setOnMenuItemClickListener(this@MarketFragment)
            }

            swipeToRefreshView.setOnRefreshListener { viewModel.reload() }

            recyclerView.apply {
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
                adapter = productAdapter
                layoutManager = LinearLayoutManager(requireContext())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    setOnScrollChangeListener { _, _, _, _, oldScrollY ->
                        fab.apply { if (oldScrollY < 0) shrink() else extend() }
                    }
            }
        }
        return binding.root
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_item_profile -> {
                findNavController().navigate(MarketFragmentDirections.actionMarketFragmentToProfileFragment())
                true
            }
            R.id.menu_cart_item -> {
                findNavController().navigate(MarketFragmentDirections.actionMarketFragmentToCartFragment())
                true
            }
            else -> false
        }

    }

    inner class ClickHandler {
        fun addToCart(index: Int) {
            viewModel.addToCart(index)
        }

        fun removeFromCart(index: Int) {
            viewModel.removeFromCart(index)
        }
    }
}