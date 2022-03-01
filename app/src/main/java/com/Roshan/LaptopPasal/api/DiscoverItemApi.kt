package com.dipesh.onlinegadgetsstore.api

import com.dipesh.onlinegadgetsstore.response.DiscoverItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface DiscoverItemApi {
    @GET("product/showAll")
    suspend fun getAllProducts(
        @Header("Authorization")token: String
    ):Response<DiscoverItemResponse>
}