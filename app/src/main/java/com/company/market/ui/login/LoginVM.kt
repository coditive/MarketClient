package com.company.market.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

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

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun attemptLogin(userName: String, password: String?) {
        _status.value = LoginState.IN_PROGRESS

        isPasswordValid(password)

        if (password != null) {
            auth.signInWithEmailAndPassword(userName, password)
                .addOnSuccessListener {
                    _status.value = LoginState.LOGGED_IN
                    _token.value = auth.currentUser?.uid
                }.addOnFailureListener{
                    _status.value = LoginState.LOGIN_ERROR
                }
        }
    }

    fun createUser(userName: String, password: String){
        _status.value = LoginState.CREATE_USER

        auth.createUserWithEmailAndPassword(userName, password)
            .addOnSuccessListener {
                _status.value = LoginState.LOGGED_IN
            }
            .addOnFailureListener {
                _status.value = LoginState.LOGIN_ERROR
            }
    }

    private fun isPasswordValid(text: String?): Boolean {
        return text != null && text.length >= 8
    }
}

enum class LoginState {
    NOT_LOGGED_IN,
    IN_PROGRESS,
    LOGGED_IN,
    LOGIN_ERROR,
    CREATE_USER
}
