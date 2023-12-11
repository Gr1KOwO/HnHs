package com.example.lessnon3_igor.presentation.ui.order

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderProduct(
    val id:String,
    val title:String,
    val department:String,
    val price:String,
    val preview:String,
    val size:String
): Parcelable
