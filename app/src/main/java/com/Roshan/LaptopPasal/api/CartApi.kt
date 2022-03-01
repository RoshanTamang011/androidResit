package com.dipesh.onlinegadgetsstore.api

import com.dipesh.onlinegadgetsstore.response.CartResponse
import com.dipesh.onlinegadgetsstore.response.DeleteCartResponse
import retrofit2.Response
import retrofit2.http.*

interface CartApi {

    @GET("user/cart/getCartItems")
    suspend fun getUserCartItems(
        @Header("Authorization") token: String
    ): Response<CartResponse>

    @POST("user/cart/addtocart/{id}")
    suspend fun addToCart(
        @Header("Authorization") token :String,
        @Path("id") id:String
    ):Response<CartResponse>


    @DELETE("user/cart/deleteCartItems/{id}")
    suspend fun deleteCart(
            @Header("Authorization") token :String,
            @Path("id") id:String
    ):Response<DeleteCartResponse>


}