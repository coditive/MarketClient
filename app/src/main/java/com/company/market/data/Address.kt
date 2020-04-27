package com.company.market.data

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Address(
    val addresse_name: String,
    val address_contact_num: Int,
    val address: String,
    val address_type: String,
    val address_pincode: Int
)