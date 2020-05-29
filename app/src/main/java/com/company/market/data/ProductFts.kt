package com.company.market.data

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey


@Fts4(contentEntity = Product::class)
@Entity(tableName = "product_fts")
data class ProductFts(
    @PrimaryKey(autoGenerate = true) val rowid: Long,
    val id: String,
    val title: String
)