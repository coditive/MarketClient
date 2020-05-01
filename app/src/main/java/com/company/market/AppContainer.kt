package com.company.market

import android.content.Context
import androidx.room.Room
import com.company.market.data.local.MarketDatabase
import com.company.market.data.remote.RemoteApi
import com.company.market.utils.BASE_URL
import com.company.market.utils.MARKET_DB_NAME
import com.company.market.utils.SHARED_PREFS_KEY
import com.company.market.utils.TOKEN_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppContainer(application: MarketApplication) {
    val remoteApi: RemoteApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    this.addInterceptor(httpLoggingInterceptor)
                }
            }.build())
            .build()
            .create(RemoteApi::class.java)

    private val db =
        Room.databaseBuilder(
            application,
            MarketDatabase::class.java,
            MARKET_DB_NAME
        ).build()

    val productDao = db.productDao()
    val userProfileDao = db.userProfileDao()
    val orderDao = db.orderDao()
    val currentUserToken = lazy {
        application.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
            .getString(TOKEN_KEY, null)
    }
}