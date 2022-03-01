package com.dipesh.onlinegadgetsstore.repository

import com.dipesh.onlinegadgetsstore.api.ApiRequest
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.api.UserApi
import com.dipesh.onlinegadgetsstore.entity.User
import com.dipesh.onlinegadgetsstore.response.ImageResponse
import com.dipesh.onlinegadgetsstore.response.LoginResponse
import okhttp3.MultipartBody

class UserRepository:ApiRequest() {
    val myApi =ServiceBuilder.buildService(UserApi::class.java)
    suspend fun registerUser(user: User): LoginResponse{
        return apiRequest {
            myApi.registerUser(user)
        }
    }

    suspend fun checkUser(email: String, password: String): LoginResponse {
        return apiRequest {
            myApi.checkUser(email, password)
        }
    }

    suspend fun updateUser  (id : String, user:User): LoginResponse {
        return apiRequest {
            myApi.updateUser(id,user)

        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            myApi.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }
}