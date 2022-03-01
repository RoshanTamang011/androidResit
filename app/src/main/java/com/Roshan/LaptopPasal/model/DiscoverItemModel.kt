package com.dipesh.onlinegadgetsstore.model

import android.os.Parcel
import android.os.Parcelable

data class DiscoverItemModel(
        val _id: String?=null,
        val productName:String?=null,
        val productDescription:String?=null,
        val productPrice:Int?=null,
        val category:String?=null,
        val productImage:String?=null,
        val quantity:String?=null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(productName)
        parcel.writeString(productDescription)
        parcel.writeValue(productPrice)
        parcel.writeString(category)
        parcel.writeString(productImage)
        parcel.writeString(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiscoverItemModel> {
        override fun createFromParcel(parcel: Parcel): DiscoverItemModel {
            return DiscoverItemModel(parcel)
        }

        override fun newArray(size: Int): Array<DiscoverItemModel?> {
            return arrayOfNulls(size)
        }
    }
}