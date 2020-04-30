package com.company.market.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.market.data.local.UserProfileDao
import com.company.market.data.remote.RemoteApi

class UserProfileRepo (
    private val localDataSource: UserProfileDao,
    private val remoteDataSource: RemoteApi,
    private val userToken: String?
){
    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    val cachedUserProfile = localDataSource.observeUserProfile()

    suspend fun loadUserProfile() {
        try{
            _loadingState.postValue(true)

            //load user profile from network
            val userProfile = remoteDataSource.getUserProfile(userToken!!)

            //cache it in database for persistence.
            localDataSource.insertUserProfile(userProfile)

        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            _loadingState.postValue(false)
        }
    }
}