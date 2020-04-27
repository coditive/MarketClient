package com.company.market.data.remote

import com.company.market.data.Product
import retrofit2.http.GET

interface RemoteApi {
    @GET("api/read-products")
    suspend fun getProducts(): List<Product>
}