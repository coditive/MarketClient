package com.company.market.data

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserOrder(
    val order_id: String,
    val order: List<Order>,
    val delivery_status: String,
    val mode_of_payment: String,
    val payment_status: String,
    val transaction_id: String,
    val timestamp: Long
)