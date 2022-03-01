package com.dipesh.onlinegadgetsstore.repository

import com.dipesh.onlinegadgetsstore.api.ApiRequest
import com.dipesh.onlinegadgetsstore.api.FavouriteApi
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.response.DeleteCartResponse
import com.dipesh.onlinegadgetsstore.response.DeleteFavItemResponse
import com.dipesh.onlinegadgetsstore.response.WishListResponse

class WishListRepository : ApiRequest(){
    private val favApi=ServiceBuilder.buildService(FavouriteApi::class.java)

    suspend fun addtofav(productId:String):WishListResponse{
        return apiRequest {
            favApi.addToFavourite(ServiceBuilder.token!!,productId)
        }
    }

    suspend fun getUserFavItems():WishListResponse{
        return apiRequest {
            favApi.getUserFavItems(ServiceBuilder.token!!)
        }
    }

    suspend fun deleteFavItem(id:String ): DeleteFavItemResponse {
        return apiRequest {
            favApi.deleteFavItem(ServiceBuilder.token!!,id)
        }
    }
}