package com.dipesh.onlinegadgetsstore.converter

import androidx.room.TypeConverter
import com.dipesh.onlinegadgetsstore.entity.User
import com.google.gson.Gson

class UserConverter {
    @TypeConverter
    fun listToJson(value:List<User>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value,Array<User>::class.java).toList()
}