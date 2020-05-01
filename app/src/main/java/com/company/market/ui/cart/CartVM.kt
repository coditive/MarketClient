package com.company.market.ui.cart

import android.util.Log
import androidx.lifecycle.*
import com.company.market.data.Order
import com.company.market.data.Product
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartVM(private val orderDao: OrderDao) : ViewModel() {

      val ordersInCart: LiveData<List<Order>> = orderDao.observeOrders()
}