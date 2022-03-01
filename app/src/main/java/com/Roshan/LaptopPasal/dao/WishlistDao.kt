package com.dipesh.onlinegadgetsstore.dao

import androidx.room.Dao
import androidx.room.Insert
import com.dipesh.onlinegadgetsstore.entity.FavouriteItem

@Dao

interface WishlistDao {
    @Insert
    suspend fun insertFavItems(wish:List<FavouriteItem>)
}