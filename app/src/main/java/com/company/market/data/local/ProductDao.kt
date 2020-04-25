package com.company.market.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.company.market.data.Product


@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun observeProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE product_id = :productId")
    suspend fun getProduct(productId: String): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteAllProduct()

    @Update
    suspend fun updateProduct(vararg product: Product)
}