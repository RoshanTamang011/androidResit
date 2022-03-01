package com.dipesh.onlinegadgetsstore.repository

import com.dipesh.onlinegadgetsstore.api.ApiRequest
import com.dipesh.onlinegadgetsstore.api.DiscoverItemApi
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.DiscoverItem
import com.dipesh.onlinegadgetsstore.response.DiscoverItemResponse

class DiscoverItemRepository:ApiRequest() {
    private val discoverItemApi=ServiceBuilder.buildService(DiscoverItemApi::class.java)
    suspend fun getProducts():DiscoverItemResponse{
        return apiRequest {
            discoverItemApi.getAllProducts(ServiceBuilder.token!!)
        }
    }
}