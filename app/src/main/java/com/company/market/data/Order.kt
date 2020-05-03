package com.company.market.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "order")
data class Order(
    @PrimaryKey val product_id: String,
    val product_name: String,
    val price: Int,
    val total_item_cost: Double,
    val quantity: Double,
    val unit: String
)