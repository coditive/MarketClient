package com.company.market.ui.cart

import android.util.Log
import androidx.lifecycle.*
import com.company.market.data.Order
import com.company.market.data.Product
import com.company.market.data.local.OrderDao
import com.company.market.data.local.ProductDao
import kotlinx.coroutines.launch

class CartVM(private val productDao: ProductDao, private val orderDao: OrderDao) : ViewModel() {

     lateinit var productsInCart: LiveData<List<Product>>
     lateinit var orders: LiveData<List<Order>>

     init {

          Log.d("CartVM", "product in Cart : ${productDao.getItemsInCart().value?.size}")
          Log.d("CartVM", "product in Cart : ${orderDao.observeOrders().value?.size}")
     }

}