package com.company.market.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order")
data class Order(
    @PrimaryKey val product_id: Int,
    val total_item_cost: Int,
    val quantity: Int
)