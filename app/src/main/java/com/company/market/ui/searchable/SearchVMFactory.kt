package com.company.market.ui.searchable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao
import com.company.market.data.remote.RemoteApi
import com.company.market.data.repos.ProductRepo

class SearchVMFactory constructor(
    private val productDao: ProductDao,
    private val orderDao: OrderDao,
    private val remoteApi: RemoteApi
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchVM::class.java)) {
            return SearchVM(ProductRepo(productDao, orderDao, remoteApi)) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }

}