package com.company.market.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.company.market.data.Product


@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun observeProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE product_id = :productId")
    suspend fun getProduct(productId: Int): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("DELETE FROM product WHERE product_id = :productId")
    suspend fun deleteProduct(productId: Int)

}