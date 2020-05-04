package com.company.market.data

import com.squareup.moshi.JsonClass
import java.sql.Timestamp


@JsonClass(generateAdapter = true)
data class UserOrders(
    val order_id: String,
    val order: List<Order>,
    val status_of_order: String,
    val mode_of_payment: String,
    val payment_status: String,
    val transaction_id: String,
    val timestamp: Long
)