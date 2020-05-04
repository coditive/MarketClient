package com.company.market.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.market.data.remote.RemoteApi

class OrdersVMFactory(private val remoteApi: RemoteApi, private val userId: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(OrdersVM::class.java)) {
            OrdersVM(remoteApi, userId) as T
        } else throw  Exception("Unsupported ViewModel")
}