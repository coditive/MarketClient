package com.company.market.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "user_profile")
data class UserProfile constructor(
    @PrimaryKey @Json(name = "id") val userId: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: Long,
    @Embedded
     val address: Address
)