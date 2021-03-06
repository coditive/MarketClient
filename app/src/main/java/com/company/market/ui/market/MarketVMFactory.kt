package com.company.market.ui.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao
import com.company.market.data.remote.RemoteApi
import com.company.market.data.repos.ProductRepo

class MarketVMFactory constructor(
    private val remoteApi: RemoteApi,
    private val productDb: ProductDao,
    private val orderDb: OrderDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(MarketVM::class.java)) {
            MarketVM(ProductRepo(productDb, orderDb,remoteApi)) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

}