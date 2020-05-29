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

    @Query("UPDATE product SET isInCart = :inCart WHERE id = :productId")
    suspend fun setIsInCart(inCart: Boolean, productId: String)

    @Query("UPDATE product SET isInCart = :inCart")
    suspend fun removeInCartItems(inCart: Boolean = false)

    //SQLite does not have a boolean data type. Room maps it to an INTEGER column, mapping true to 1 and false to 0.
    @Query("SELECT * FROM product WHERE isInCart = :isInCart")
    fun getItemsInCart(isInCart: Boolean = true): LiveData<List<Product>>

    @Query("SELECT * FROM product JOIN product_fts ON product_fts.id = product.id WHERE product_fts.title MATCH :title")
    suspend fun searchProduct(title: String): List<Product>


    @Query("INSERT INTO product_fts(product_fts) VALUES ('rebuild')")
    suspend fun rebuildFts()

}