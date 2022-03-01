package com.dipesh.raew.api

import com.dipesh.raew.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {


    //Login user
    @FormUrlEncoded
    @POST("login")
    suspend fun checkUser(
        @Field("email") email : String,
        @Field("password") password : String
    ): Response<LoginResponse>

}