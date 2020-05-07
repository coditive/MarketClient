package com.company.market.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.market.MarketApplication
import com.company.market.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this, OrdersVMFactory(
                (requireContext().applicationContext as MarketApplication).appContainer.remoteApi,
                (requireContext().applicationContext as MarketApplication).appContainer.currentUserToken!!
            )
        ).get(OrdersVM::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val orderAdapter = OrdersAdapter()
        binding.apply {
            recyclerView.apply {
                adapter = orderAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }
            swipeToRefreshView.setOnRefreshListener { viewModel.reload() }
        }
        viewModel.apply {
            orderList.observe(viewLifecycleOwner) { list ->
                orderAdapter.submitList(list.sortedByDescending {it.timestamp })
            }
            loading.observe(viewLifecycleOwner) {
                binding.swipeToRefreshView.isRefreshing = it
            }
        }
        return binding.root
    }
}
