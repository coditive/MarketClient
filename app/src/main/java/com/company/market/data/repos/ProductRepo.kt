package com.company.market.data.repos

import androidx.lifecycle.LiveData
import com.company.market.data.Product
import com.company.market.data.Result
import com.company.market.data.Result.Error
import com.company.market.data.Result.Success
import com.company.market.data.UserProfile
import com.company.market.data.local.LocalDataSource
import com.company.market.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ProductRepo (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private suspend fun updateProductsFromRemoteDataSource() {
        val remoteProducts = remoteDataSource.getProductsFromFirestore()

        if(remoteProducts is Success){
            localDataSource.deleteAllProduct()
            remoteProducts.data.forEach{
                product ->
                localDataSource.saveProduct(product)
            }
        } else if (remoteProducts is Error) {
            throw remoteProducts.exception
        }
    }

    fun observerProducts(): LiveData<Result<List<Product>>> {
        return localDataSource.observeStoredProduct()
    }

    suspend fun getProfile(): Result<UserProfile?> {
        lateinit var userProfile: Result<UserProfile?>
        coroutineScope {
            launch { userProfile = remoteDataSource.getUserProfile()}
            if(userProfile is Success){
                launch { (userProfile as Success<UserProfile?>).data?.let { localDataSource.saveUserProfile(it) } }
            }
        }
        return userProfile
    }
}