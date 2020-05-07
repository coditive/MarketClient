package com.company.market.ui.register

import android.util.Log
import androidx.lifecycle.*
import com.company.market.data.Address
import com.company.market.data.UserSignUp
import com.company.market.data.remote.RemoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegisterVM(private val remoteApi: RemoteApi) : ViewModel() {

    private val _status: MutableLiveData<RegistrationState> = MutableLiveData(RegistrationState.NOT_REGISTERED)
    val status: LiveData<RegistrationState>
        get() = _status

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean>
    get() = _loading

    fun attemptRegister(name: String, email: String, phone: String, loc: String, pin: String){
        when{
            name.isEmpty() -> _status.value = RegistrationState.NAME_ERROR
            email.isEmpty() -> _status.value = RegistrationState.EMAIL_ERROR
            phone.isEmpty() -> _status.value = RegistrationState.PHONE_ERROR
            loc.isEmpty() -> _status.value = RegistrationState.ADDRESS_ERROR
            pin.isEmpty() -> _status.value = RegistrationState.PINCODE_ERROR
            phone.length != 10 -> _status.value = RegistrationState.PHONE_ERROR
            !email.contains("@") -> _status.value = RegistrationState.EMAIL_ERROR
            pin.length != 6 -> _status.value = RegistrationState.PINCODE_ERROR
            !name.contains(" ") -> _status.value = RegistrationState.NAME_ERROR
            else -> {
                try {
                    _loading.value = true
                    val nameList: List<String> = name.split(" ")
                    viewModelScope.launch(Dispatchers.IO) {
                        try{
                            remoteApi.createUser(UserSignUp(nameList.first(), nameList.last(),
                                phone.toLong(), email, Address(loc, "home", pin.toInt())
                            ))
                        }catch (e: Exception){
                           Log.d("Error", e.message)
                        }
                    }
                    _status.value = RegistrationState.REGISTERED
                } catch (e: Exception){
                    _status.value = RegistrationState.NAME_ERROR
                } finally {
                    _loading.value = false
                }

            }
        }

    }
}

enum class RegistrationState {
    REGISTERED,
    NAME_ERROR,
    PHONE_ERROR,
    EMAIL_ERROR,
    ADDRESS_ERROR,
    PINCODE_ERROR,
    NOT_REGISTERED
}
