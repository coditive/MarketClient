package com.company.market.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.UserSignUp
import com.company.market.data.remote.RemoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterVM(private val remoteApi: RemoteApi) : ViewModel() {
    fun attemptRegister(userSignUp: UserSignUp){
        viewModelScope.launch(Dispatchers.IO) {
            remoteApi.createUser(userSignUp)
        }
    }
}