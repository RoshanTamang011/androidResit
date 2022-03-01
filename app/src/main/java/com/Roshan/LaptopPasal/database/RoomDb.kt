package com.dipesh.onlinegadgetsstore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dipesh.onlinegadgetsstore.converter.ProductConverter
import com.dipesh.onlinegadgetsstore.converter.UserConverter
import com.dipesh.onlinegadgetsstore.dao.CartDao
import com.dipesh.onlinegadgetsstore.dao.ProductDao
import com.dipesh.onlinegadgetsstore.dao.WishlistDao
import com.dipesh.onlinegadgetsstore.entity.Cart
import com.dipesh.onlinegadgetsstore.entity.DiscoverItem
import com.dipesh.onlinegadgetsstore.entity.FavouriteItem
import com.dipesh.onlinegadgetsstore.entity.User

@Database(
    entities=[DiscoverItem::class, Cart::class,FavouriteItem::class],
    version=1
)
@TypeConverters(ProductConverter::class,UserConverter::class)

abstract class RoomDb:RoomDatabase() {
    abstract  fun getProductInfo():ProductDao
    abstract  fun getCartInfo():CartDao
    abstract fun getFavInfo(): WishlistDao

    companion object{
        @Volatile
        private var instance:RoomDb?=null
        fun getInstance(context: Context):RoomDb{
            if(instance==null){
                synchronized(RoomDb::class){
                    instance=buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                RoomDb::class.java,
                "RoomDb"
            ).build()
    }
}