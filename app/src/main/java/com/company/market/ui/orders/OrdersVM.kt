package com.company.market.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.UserOrder
import com.company.market.data.remote.RemoteApi
import kotlinx.coroutines.launch

class OrdersVM(private val remoteApi: RemoteApi, private val userId: String) : ViewModel() {
    private val _orderList: MutableLiveData<List<UserOrder>> = MutableLiveData()
    val orderList: LiveData<List<UserOrder>> = _orderList

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    init {
        reload()
    }

    fun reload() {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                _orderList.postValue(remoteApi.getUserOrders(userId))
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.postValue(false)
            }
        }
    }
}