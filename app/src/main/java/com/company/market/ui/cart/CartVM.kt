package com.company.market.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.company.market.data.Order
import com.company.market.data.Product
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao

class CartVM(private val productDao: ProductDao, private val orderDao: OrderDao) : ViewModel() {
    val uiModel: MediatorLiveData<List<Pair<Order, Product>>> = MediatorLiveData()

    private val productsInCart: LiveData<List<Product>> = productDao.getItemsInCart()
    private val orders: LiveData<List<Order>> = orderDao.observeOrders()

}