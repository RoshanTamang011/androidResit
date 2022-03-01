package com.dipesh.onlinegadgetsstore.response

import com.dipesh.onlinegadgetsstore.entity.Cart

class CartResponse (
        val success : Boolean?=null,
        val data : MutableList<Cart>?
        )