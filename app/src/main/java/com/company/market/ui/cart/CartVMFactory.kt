package com.company.market.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao


class CartVMFactory constructor(
    private val orderDb: OrderDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(CartVM::class.java)) {
            CartVM(orderDb) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

}