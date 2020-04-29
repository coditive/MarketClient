package com.company.market.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.market.data.local.UserProfileDao
import com.company.market.data.remote.RemoteApi

class UserProfileRepo (
    private val localDataSource: UserProfileDao,
    private val remoteDataSource: RemoteApi
){
    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    val cachedUserProfile = localDataSource.observeUserProfile()

    suspend fun loadUserProfile(userId: String){
        try{
            _loadingState.value = true

            //load user profile from network
            val userProfile = remoteDataSource.getUserProfile(userId)

            //cache it in database for persistence.
            localDataSource.insertUserProfile(userProfile)

        } catch (e: Exception){

        } finally {
            _loadingState.value = false
        }
    }
}