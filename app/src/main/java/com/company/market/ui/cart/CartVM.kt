package com.company.market.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.Order
import com.company.market.data.local.OrderDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartVM(private val orderDao: OrderDao) : ViewModel() {

    val ordersInCart: LiveData<List<Order>> = orderDao.observeOrders()

    val totalSum: LiveData<Int> = Transformations.map(ordersInCart, ::sumOfOrders)

    fun increaseQuantity(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = ordersInCart.value?.get(index)
            orderDao.setQuantity(order!!.quantity + 1, order.product_id)
        }
    }

    private fun sumOfOrders(list: List<Order>) = list.sumBy { it.price * it.quantity }
    fun decreaseOrder(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = ordersInCart.value?.get(index)
            if (order!!.quantity - 1 == 0) orderDao.deleteOrder(order.product_id)
            else orderDao.setQuantity(order.quantity - 1, order.product_id)
        }
    }
}