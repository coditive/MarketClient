package com.company.market.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.company.market.data.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM 'order'")
    fun observeOrders(): LiveData<List<Order>>

    @Query("UPDATE `order` SET quantity = :newQuantity WHERE product_id = :id")
    suspend fun setQuantity(newQuantity: Int, id: String)

    @Query("DELETE FROM `order` WHERE product_id = :id")
    suspend fun deleteOrder(id: String)
}