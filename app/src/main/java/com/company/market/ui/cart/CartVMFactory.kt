package com.company.market.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao
import com.company.market.data.repos.ProductRepo
import com.company.market.ui.market.MarketVM

class CartVMFactory constructor(
    private val productDb: ProductDao,
    private val orderDb: OrderDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(CartVM::class.java)) {
            CartVM(productDb, orderDb) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

}