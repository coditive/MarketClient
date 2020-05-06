package com.company.market.data.remote

import com.company.market.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RemoteApi {
    @GET("api/read-products")
    suspend fun getProducts(): List<Product>

    @GET("api/get_user_profile/{user_id}")
    suspend fun getUserProfile(@Path("user_id")userId: String): UserProfile

    @POST("api/order_products/{user_id}")
    suspend fun orderProducts(@Path("user_id")userId: String, @Body jsonBody: Checkout)

    @GET("api/user_orders/{user_id}")
    suspend fun getUserOrders(@Path("user_id") userId: String): List<UserOrder>

    @POST("api/create_user")
    suspend fun createUser(@Body user: UserSignUp)
}