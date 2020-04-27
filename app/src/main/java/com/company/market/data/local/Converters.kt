package com.company.market.data.local

import androidx.room.TypeConverter
import com.company.market.data.Address

class Converters {

    @TypeConverter
    fun fromJsonString(value: String): List<Address> {
        //TODO() : convert jsonString to list of addresses using moshi.
        return emptyList()
    }

    @TypeConverter
    fun toJsonString(addressList: List<Address>): String {
        // TODO() : convert to jsonTree which can be stored as string in database.
        return ""
    }

}