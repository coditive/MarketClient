package com.company.market.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_profile")
data class UserProfile constructor(
    @PrimaryKey @ColumnInfo val userId: String,
    @ColumnInfo val userName: String,
    @ColumnInfo val address: String,
    @ColumnInfo val phoneNumber: String
)