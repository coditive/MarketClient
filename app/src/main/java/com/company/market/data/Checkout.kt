package com.company.market.data

import com.squareup.moshi.JsonClass

/**
 * This data class will content values required for making order.
 * @property order will specify all the ordered product which quantity, id and total_cost for that quantity.
 * @property total_cost will specify the total_cost of all products combined together.
 */

@JsonClass(generateAdapter = true)
data class Checkout (
    val order: List<Order>,
    val total_cost: Int,
    val delivery_status: String,
    val mode_of_payment: String,
    val payment_status: String
)

