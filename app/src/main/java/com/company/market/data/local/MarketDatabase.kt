package com.company.market.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.market.data.Order
import com.company.market.data.Product
import com.company.market.data.UserProfile


@Database(
    entities = [UserProfile::class, Product::class, Order::class],
    version = 1,
    exportSchema = false
)
abstract class MarketDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun orderDao(): OrderDao
}