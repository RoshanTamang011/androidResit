package com.dipesh.onlinegadgetsstore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dipesh.onlinegadgetsstore.entity.DiscoverItem

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product:List<DiscoverItem>?)
//
//    @Query("select * from `DiscoverItem`")
//    suspend fun deleteMatch()

}