package com.company.market.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.market.data.remote.RemoteApi

class RegisterVMFactory(private val remoteApi: RemoteApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(RegisterVM::class.java))
            RegisterVM(remoteApi) as T
        else throw Exception("Invalid ViewModel")
}