package com.dipesh.onlinegadgetsstore.repository

import com.dipesh.onlinegadgetsstore.api.ApiRequest
import com.dipesh.onlinegadgetsstore.api.CartApi
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.Cart
import com.dipesh.onlinegadgetsstore.response.CartResponse
import com.dipesh.onlinegadgetsstore.response.DeleteCartResponse

class CartRepository:ApiRequest() {
    private val cartApi=ServiceBuilder.buildService(CartApi::class.java)
    suspend fun getUserCartItems():CartResponse{
        return apiRequest {
            cartApi.getUserCartItems(ServiceBuilder.token!!)
        }
    }

    suspend fun addToCart(productId:String):CartResponse{
        return apiRequest {
            cartApi.addToCart(ServiceBuilder.token!!,productId)
        }
    }

    suspend fun deleteCart(id:String ):DeleteCartResponse{
        return apiRequest {
            cartApi.deleteCart(ServiceBuilder.token!!,id)
        }
    }
}