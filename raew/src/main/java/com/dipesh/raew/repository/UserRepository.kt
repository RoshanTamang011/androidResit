package com.dipesh.raew.repository

import com.dipesh.raew.api.ApiRequest
import com.dipesh.raew.api.ServiceBuilder
import com.dipesh.raew.api.UserApi
import com.dipesh.raew.response.LoginResponse

class UserRepository:ApiRequest() {
    val myApi = ServiceBuilder.buildService(UserApi::class.java)
    suspend fun checkUser(email: String, password: String): LoginResponse {
        return apiRequest {
            myApi.checkUser(email, password)
        }
    }
}