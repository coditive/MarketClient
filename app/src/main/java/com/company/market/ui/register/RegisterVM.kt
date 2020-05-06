package com.company.market.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.remote.RemoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterVM(private val remoteApi: RemoteApi) : ViewModel() {
    fun attemptRegister(){
        viewModelScope.launch(Dispatchers.IO) {

            //TODO: insert register code here
        }
    }
}