package com.company.market.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product")
data class Product(
    @PrimaryKey @ColumnInfo(name = "product_id") val id: Int,
    @ColumnInfo(name = "product_name") val title: String,
    @ColumnInfo(name = "product_price") val price: Int,
    @ColumnInfo(name = "product_stock")val stock: Int
)