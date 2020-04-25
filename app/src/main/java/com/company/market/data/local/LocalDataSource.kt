package com.company.market.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.company.market.data.Product
import com.company.market.data.Result
import com.company.market.data.Result.Error
import com.company.market.data.Result.Success
import com.company.market.data.UserProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource internal constructor (
    private val userProfileDao: UserProfileDao,
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun observeStoredProduct(): LiveData<Result<List<Product>>> {
        return productDao.observeProduct().map {
            Success(it)
        }
    }

    fun observeUserProfile(): LiveData<Result<UserProfile>> {
        return userProfileDao.observeUserProfile().map {
            Success(it)
        }
    }


    suspend fun saveProduct (product: Product) = withContext(ioDispatcher) {
        try {
            val result = productDao.getProduct(product.id)
            if(result == null){
                productDao.insertProduct(product)
                return@withContext Success(product)
            } else {
                return@withContext Error(Exception("Product Already Exists"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    suspend fun deleteAllProduct () = withContext(ioDispatcher) {
        try{
            productDao.deleteAllProduct()
            return@withContext Success(true)
        } catch (e: Exception){
            return@withContext Error(e)
        }
    }

    suspend fun saveUserProfile(userProfile: UserProfile) = withContext(ioDispatcher) {
        try {
            userProfileDao.insertUserProfile(userProfile)
            return@withContext Success(userProfile)
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

}