package com.company.market.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.company.market.data.Order

@Dao
interface OrderDao {

    @Query("SELECT * FROM 'order'")
    fun observeOrders(): LiveData<List<Order>>

    @Query("UPDATE `order` SET quantity = :newQuantity, total_item_cost = :cost WHERE product_id = :id")
    suspend fun setQuantity(newQuantity: Double, cost: Double,id: String)

    @Query("SELECT * FROM `order` WHERE product_id = :id")
    suspend fun getOrder(id: String): Order

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Query("DELETE FROM `order`")
    suspend fun removeAllOrders()

    @Query("DELETE FROM `order` WHERE product_id = :id")
    suspend fun deleteOrder(id: String)
}