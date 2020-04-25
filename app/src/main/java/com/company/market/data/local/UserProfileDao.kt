package com.company.market.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.company.market.data.UserProfile


@Dao
interface UserProfileDao {

    @Query("SELECT * FROM user_profile")
    fun observeUserProfile(): LiveData<UserProfile>

    @Query("SELECT * FROM user_profile")
    fun getUserProfile(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)

}