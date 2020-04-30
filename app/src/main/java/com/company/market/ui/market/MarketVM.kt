package com.company.market.ui.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.Order
import com.company.market.data.Product
import com.company.market.data.repos.ProductRepo
import kotlinx.coroutines.launch

class MarketVM(private val productRepo: ProductRepo) : ViewModel() {

    val productList: LiveData<List<Product>> = productRepo.productInMemory

    val loadingState: LiveData<Boolean> = productRepo.loadingState

    fun reload() {
        viewModelScope.launch {
            productRepo.loadProducts()
        }
    }

    fun addToCart(position: Int) {
        viewModelScope.launch {
            productRepo.addToCart(position)
        }

    }

    fun removeFromCart(position: Int) {
        viewModelScope.launch { productRepo.removeFromCart(position) }
    }
}