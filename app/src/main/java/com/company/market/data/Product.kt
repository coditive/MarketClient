package com.company.market.data

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Entity(tableName = "product")
data class Product(
    @PrimaryKey val id: String,
    val title: String,
    val price: Int,
    val quantity: Int,
    val unit: String,
    val inStock: Boolean,

    //not in backend
    val isInCart: Boolean = false
)
