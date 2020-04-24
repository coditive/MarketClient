package com.company.market.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginVM : ViewModel() {

    /**
     * null value denotes user is not logged in
     * string value represents a valid user token is available on login completion
     */

    private val _token: MutableLiveData<String?> = MutableLiveData(null)
    val token: LiveData<String?> = _token

    private val _status: MutableLiveData<LoginState> = MutableLiveData(
        LoginState.NOT_LOGGED_IN
    )
    val status: LiveData<LoginState> = _status
    fun attemptLogin(userName: String, password: String) {
        viewModelScope.launch {
            _status.postValue(LoginState.IN_PROGRESS)
            delay(2000)
            // insert login query here
            _status.postValue(LoginState.LOGGED_IN)
        }
    }
}

enum class LoginState {
    NOT_LOGGED_IN,
    IN_PROGRESS,
    LOGGED_IN,
    LOGIN_ERROR
}
