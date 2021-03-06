package com.dipesh.onlinegadgetsstore.api

import com.dipesh.onlinegadgetsstore.entity.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
   private const val BASE_URL = "http://10.0.2.2:2000/api/"
  // private const val BASE_URL = "http://localhost:2000/api/"
//    private const val BASE_URL = "http://192.168.1.1:2000/api/"
    var token :String? = null
    var userInfo: User?=null
    private val okHttp = OkHttpClient.Builder()
    private val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
    //create retrofit instance
    private val retrofit = retrofitBuilder.build()
    //generic function
    fun <T> buildService(serviceType: Class<T>):T {
        return retrofit.create(serviceType)
    }


    //load image
    fun loadImagePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/"
    }
}