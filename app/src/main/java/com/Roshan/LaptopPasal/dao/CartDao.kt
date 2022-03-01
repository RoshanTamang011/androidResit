package com.dipesh.onlinegadgetsstore.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.dipesh.onlinegadgetsstore.entity.Cart

@Dao
interface CartDao {
    @Insert
    suspend fun insertCart(cart:List<Cart>)

    @Delete
    suspend fun deleteStudents(cart:Cart)
}