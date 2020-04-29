package com.company.market.utils

import com.company.market.data.Checkout
import com.company.market.data.CheckoutJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * This function is a ext fun, that takes
 * @param moshi
 * and converts in to json object which is used as body to network call
 */

fun Checkout.toJson(moshi: Moshi) : String {
    val jsonAdapter = CheckoutJsonAdapter(moshi)
    return jsonAdapter.toJson(this)
}