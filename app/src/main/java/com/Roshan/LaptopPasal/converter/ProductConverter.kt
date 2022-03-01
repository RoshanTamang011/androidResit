package com.dipesh.onlinegadgetsstore.converter

import androidx.room.TypeConverter
import com.dipesh.onlinegadgetsstore.entity.DiscoverItem
import com.google.gson.Gson

class ProductConverter {
    @TypeConverter
    fun listToJson(value:List<DiscoverItem>?)= Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value,Array<DiscoverItem>::class.java).toList()


}