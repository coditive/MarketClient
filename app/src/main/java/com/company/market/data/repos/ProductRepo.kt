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

}