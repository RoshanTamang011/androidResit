package com.dipesh.onlinegadgetsstore.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavouriteItem (
        val _id: String="",
        val  wishItems: List<DiscoverItem>?=null,
        val user : List<User>?=null
        ){
        @PrimaryKey(autoGenerate = true)
        var id:Int=0
}