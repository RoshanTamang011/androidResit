package com.dipesh.onlinegadgetsstore.response

import com.dipesh.onlinegadgetsstore.entity.DiscoverItem
import com.dipesh.onlinegadgetsstore.model.DiscoverItemModel

data class DiscoverItemResponse (
    val success:Boolean?=null,
    val data : MutableList<DiscoverItem>?=null
        )
