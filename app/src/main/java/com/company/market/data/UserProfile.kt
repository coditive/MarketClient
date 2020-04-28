package com.company.market.data

import androidx.room.*
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "user_profile")
data class UserProfile constructor(
    @PrimaryKey  val userId: String,
     val first_name: String,
     val last_name: String,
     val email: String,
     val phone: Int,
    @Embedded
     val address: Address
)