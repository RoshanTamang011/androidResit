package com.dipesh.onlinegadgetsstore.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class DiscoverItem(

        val _id: String?=null,
        val name:String?=null,
        val price:Int?=null,
        val quantity:String?=null,
        val description:String?=null,
        val productPictures:String?=null,
        val category:String?=null
       // val reviews:String?=null
):Parcelable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(quantity)
        parcel.writeString(description)
        parcel.writeString(productPictures)
        parcel.writeString(category)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiscoverItem> {
        override fun createFromParcel(parcel: Parcel): DiscoverItem {
            return DiscoverItem(parcel)
        }

        override fun newArray(size: Int): Array<DiscoverItem?> {
            return arrayOfNulls(size)
        }
    }
}


