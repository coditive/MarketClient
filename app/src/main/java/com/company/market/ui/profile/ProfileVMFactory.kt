package com.company.market.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.market.data.local.UserProfileDao
import com.company.market.data.remote.RemoteApi
import com.company.market.data.repos.UserProfileRepo

class ProfileVMFactory(
    private val remoteApi: RemoteApi,
    private val userProfileDao: UserProfileDao,
    private val userToken: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileVM::class.java)) {
            return ProfileVM(
                UserProfileRepo(
                    userProfileDao, remoteApi, userToken
                )
            ) as T
        } else throw Exception("Unsupported ViewModel")
    }
}