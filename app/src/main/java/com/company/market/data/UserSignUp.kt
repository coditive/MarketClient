package com.company.market.data

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserSignUp(
    val first_name: String,
    val last_name: String,
    val phone: Long,
    val email: String,
    val address: Address
)