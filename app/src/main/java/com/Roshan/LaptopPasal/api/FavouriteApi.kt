package com.dipesh.onlinegadgetsstore.api

import com.dipesh.onlinegadgetsstore.entity.FavouriteItem
import com.dipesh.onlinegadgetsstore.response.DeleteCartResponse
import com.dipesh.onlinegadgetsstore.response.DeleteFavItemResponse
import com.dipesh.onlinegadgetsstore.response.WishListResponse
import retrofit2.Response
import retrofit2.http.*

interface FavouriteApi {

    @POST("user/wish/addtofav/{id}")
    suspend fun addToFavourite(
            @Header("Authorization") token:String,
            @Path("id") id:String
    ):Response<WishListResponse>

    @GET("user/wish/getfavitems")
    suspend fun getUserFavItems(
            @Header("Authorization") token: String

    ):Response<WishListResponse>

    @DELETE("user/wish/deleteFavItems/{id}")
    suspend fun deleteFavItem(
        @Header("Authorization") token :String,
        @Path("id") id:String
    ):Response<DeleteFavItemResponse>

}