package com.company.market.ui.searchable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.Product
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao
import com.company.market.data.remote.RemoteApi
import com.company.market.data.repos.ProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchVM (
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _searchedProductList = MutableLiveData<List<Product>>()
    val searchedProductList: LiveData<List<Product>> = _searchedProductList

    val loadingState: LiveData<Boolean> = productRepo.loadingState


    fun searchProduct(productName: String){
       viewModelScope.launch {
           _searchedProductList.value = productRepo.searchProductFromText(productName)
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

    fun removeAllFromCart(){
        viewModelScope.launch(Dispatchers.IO) { productRepo.removeAllItems() }
    }
}