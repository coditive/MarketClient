package com.company.market.ui.market

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.market.R
import com.company.market.data.Product
import com.company.market.databinding.FragmentMarketBinding

class MarketFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMarketBinding.inflate(inflater, container, false)
        binding.apply {
            toolbar.setupWithNavController(findNavController())
            toolbar.setOnMenuItemClickListener(this@MarketFragment)
            recyclerView.apply {
                val list: MutableList<Product> = mutableListOf()
                repeat(50) {
                    list.add(
                        Product("some product", it, it)
                    )
                }
                adapter = ProductAdapter(list)
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
        return if (item?.itemId == R.id.menu_item_profile) {
            findNavController().navigate(MarketFragmentDirections.actionMarketFragmentToProfileFragment())
            true
        } else false

    }
}