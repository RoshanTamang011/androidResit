package com.dipesh.onlinegadgetsstore.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
//@Entity
data class User(
        val _id :String?=null,
        val firstName :String?=null,
        val lastName :String?=null,
        val username:String?=null,
        val email:String?=null,
        val password:String?=null


        )

//{
//    @PrimaryKey(autoGenerate = true)
//    var id: Int=0
//}
