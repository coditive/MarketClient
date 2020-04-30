package com.company.market.data.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.market.data.Product
import com.company.market.data.local.ProductDao
import com.company.market.data.remote.RemoteApi
import java.io.IOException

class ProductRepo(
    private val localDataSource: ProductDao,
    private val remoteDataSource: RemoteApi
) {
    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    //loading from cached db
    val productInMemory: LiveData<List<Product>> = localDataSource.observeProduct()

    suspend fun loadProducts() {
        try {
            _loadingState.value = true
            //fetch from internet
            val fetchedProducts = remoteDataSource.getProducts()

            //update db cache
            fetchedProducts.forEach { localDataSource.insertProduct(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            _loadingState.value = false
        }
    }

    suspend fun addToCart(position: Int) {
        val selectedProduct = productInMemory.value?.get(position)
        if (selectedProduct != null) {
            localDataSource.setIsInCart(true, selectedProduct.id)
        } else Log.e(TAG, "item at position $position not found in memory ")
    }

    suspend fun removeFromCart(position: Int) {
        val selectedProduct = productInMemory.value?.get(position)
        if (selectedProduct != null) {
            localDataSource.setIsInCart(false, selectedProduct.id)
        } else Log.e(TAG, "item at position $position not found in memory ")
    }

    companion object {
        private const val TAG = "ProductRepo"
    }
}