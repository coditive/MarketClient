package com.company.market.data.repos

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

    private val _productsInMemory: MutableLiveData<List<Product>> = MutableLiveData(listOf())
    val productInMemory: LiveData<List<Product>> = _productsInMemory

    suspend fun loadProducts() {
        try {
            _loadingState.value = true
            //loading from cached db
            _productsInMemory.value = localDataSource.getAllProducts()

            //fetch from internet
            val fetchedProducts = remoteDataSource.getProducts()

            //update db cache
            fetchedProducts.forEach { localDataSource.insertProduct(it) }

            _productsInMemory.value = fetchedProducts
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            _loadingState.value = false
        }
    }
}