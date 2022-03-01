package com.dipesh.onlinegadgetsstore.api

import com.dipesh.onlinegadgetsstore.entity.User
import com.dipesh.onlinegadgetsstore.response.ImageResponse
import com.dipesh.onlinegadgetsstore.response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    //Register user
    @POST("signup")
    suspend fun registerUser(
            @Body user: User
    ):Response<LoginResponse>

    //Login user
    @FormUrlEncoded
    @POST("login")
    suspend fun checkUser(
            @Field("email") email : String,
            @Field("password") password : String
    ):Response<LoginResponse>


    //Update user

    @PUT("user/edit/{id}")
    suspend fun updateUser(
        @Path("id") id:String,
        @Body user:User
    ): Response<LoginResponse>


    @Multipart
    @PUT("user/uploadImage/{id}")
    suspend fun uploadImage(
            @Header("Authorization") token: String,
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>

}