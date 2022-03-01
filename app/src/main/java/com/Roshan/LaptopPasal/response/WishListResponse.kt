package com.dipesh.onlinegadgetsstore.response

import com.dipesh.onlinegadgetsstore.entity.Cart
import com.dipesh.onlinegadgetsstore.entity.FavouriteItem

class WishListResponse (
        val success : Boolean?=null,
        val data : MutableList<FavouriteItem>?
        )