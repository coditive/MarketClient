package com.company.market.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.Checkout
import com.company.market.data.Order
import com.company.market.data.local.OrderDao
import com.company.market.data.remote.RemoteApi
import com.company.market.data.repos.ProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartVM(private val orderDao: OrderDao,
             private val remoteApi: RemoteApi,
             private val productRepo: ProductRepo) : ViewModel() {

    val ordersInCart: LiveData<List<Order>> = orderDao.observeOrders()

    val totalSum: LiveData<Double> = Transformations.map(ordersInCart, ::sumOfOrders)

    fun increaseQuantity(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = ordersInCart.value?.get(index)
            orderDao.setQuantity(order!!.quantity + 0.25, ((order.quantity + 0.25) * order.price),order.product_id)
        }
    }

    private fun sumOfOrders(list: List<Order>) = list.sumByDouble { it.price * it.quantity }

    fun decreaseOrder(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = ordersInCart.value?.get(index)
            if ((order!!.quantity - 0.25) == 0.0) orderDao.deleteOrder(order.product_id)
            else orderDao.setQuantity(order.quantity - 0.25, ((order.quantity + 0.25) * order.price), order.product_id)
        }
    }

    fun orderProducts(userId: String, totalSum: Double, orders: List<Order>) {
        val checkOut = Checkout(
            orders, totalSum.toString(), "in_process",
            "in_cash", "not_paid"
        )

        viewModelScope.launch(Dispatchers.IO) {
            orders.forEach {
                productRepo.removeFromCartUsingId(it.product_id)
            }

            remoteApi.orderProducts(userId, checkOut)
        }
    }
}
