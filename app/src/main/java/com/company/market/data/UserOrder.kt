package com.company.market.data

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserOrder(
    val order_id: String,
    val order: List<Order>,
    val status_of_order: String = "Not completed",  //unable to get anything from backend so fix it!
    val mode_of_payment: String,
    val payment_status: String,
    val transaction_id: String,
    val timestamp: Long
)