package com.dipesh.onlinegadgetsstore.response

import com.dipesh.onlinegadgetsstore.entity.User

class LoginResponse (
    val success:Boolean?=null,
    val token:String?=null,
    val data: User?=null
)