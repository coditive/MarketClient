package com.company.market.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.market.data.UserProfile
import com.company.market.data.repos.UserProfileRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileVM(private val profileRepo: UserProfileRepo) : ViewModel() {
    val profileModel: LiveData<UserProfile> = profileRepo.cachedUserProfile
    val loadingState: LiveData<Boolean> = profileRepo.loadingState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepo.loadUserProfile()
        }
    }

    fun refreshProfile() = viewModelScope.launch(Dispatchers.IO) { profileRepo.loadUserProfile() }
}