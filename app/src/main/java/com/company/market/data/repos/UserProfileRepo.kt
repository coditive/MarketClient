package com.company.market.data.repos

import com.company.market.data.local.UserProfileDao
import com.company.market.data.remote.RemoteApi

class UserProfileRepo (
    private val localDataSource: UserProfileDao,
    private val remoteDataSource: RemoteApi
)