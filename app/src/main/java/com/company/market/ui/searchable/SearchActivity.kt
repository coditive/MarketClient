package com.company.market.ui.searchable

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.market.MarketApplication
import com.company.market.R
import com.company.market.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            SearchVMFactory (
                (this.application as MarketApplication).appContainer.productDao,
                (this.application as MarketApplication).appContainer.orderDao,
                (this.application as MarketApplication).appContainer.remoteApi
            )
        ).get(SearchVM::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val searchProductAdapter = SearchProductAdapter(ClickHandler())

        handleIntent(intent!!)

        viewModel.apply {
            loadingState.observe(this@SearchActivity) { loading ->
                binding.swipeToRefreshView.isRefreshing = loading
            }
            searchedProductList.observe(this@SearchActivity) {
                Log.d("SearchActivity", "List of Searched products are : $it")
                searchProductAdapter.submitList(it)
            }
        }

        binding.apply {

            toolbar.setTitle(R.string.search_activity)

            searchRecyclerView.apply {
                addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
                adapter = searchProductAdapter
                layoutManager = LinearLayoutManager(this@SearchActivity)
            }
        }

    }


    private fun handleIntent(intent: Intent) {
        if(Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d("SearchActivity", "List of products are : $query")
            viewModel.searchProduct(query)
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