package com.company.market.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.Product
import kotlinx.coroutines.launch

/**
 * @property cartItemList is a list of products and their quantities in a cart
 **/
class ActivityVM : ViewModel() {
    private val _cartItemsList: MutableLiveData<List<Pair<Product, Int>>> =
        MutableLiveData(emptyList())
    val cartItemList: LiveData<List<Pair<Product, Int>>> = _cartItemsList

    suspend fun addItem(itemId: String) {
        val currentList = _cartItemsList.value?.toMutableList()

        viewModelScope.launch {
            //insert room db code here
            currentList?.add(
                Pair(
                    Product(id = itemId, title = itemId, price = 100, inStock = 100),
                    2
                )
            )
        }
        _cartItemsList.value = currentList?.toList() ?: listOf()
    }

    fun removeItem(itemId: String) {
        val currentList = _cartItemsList.value?.toMutableList()
        _cartItemsList.value = currentList?.filter { it.first.id == itemId }?.toList() ?: listOf()
    }

    fun increaseQuantity(index: Int) {
        val list = _cartItemsList.value?.toMutableList()
        list?.let {
            it[index] = it[index].copy(second = it[index].second + 1)
            _cartItemsList.value = it.toList()
        }
    }

    fun decreaseQuantity(index: Int) {
        val list = _cartItemsList.value?.toMutableList()
        list?.let {
            it[index] =
                if (it[index].second - 1 > 0) it[index].copy(second = it[index].second - 1) else it[index]
            _cartItemsList.value = it.toList()
        }
    }

    companion object {
        private const val TAG = "ActivityVM"
    }
}