package com.company.market.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.company.market.data.Product


@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun observeProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE id = :productId")
    suspend fun getProduct(productId: String): Product?

    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAllProduct()

    @Update
    suspend fun updateProduct(vararg product: Product)
}