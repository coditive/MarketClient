package com.company.market.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao
import com.company.market.data.remote.RemoteApi
import com.company.market.data.repos.ProductRepo


@Suppress("UNCHECKED_CAST")
class CartVMFactory constructor(
    private val orderDb: OrderDao,
    private val remoteDb: RemoteApi,
    private val productDao: ProductDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(CartVM::class.java)) {
            CartVM(orderDb, remoteDb, ProductRepo(productDao,orderDb, remoteDb)) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

}