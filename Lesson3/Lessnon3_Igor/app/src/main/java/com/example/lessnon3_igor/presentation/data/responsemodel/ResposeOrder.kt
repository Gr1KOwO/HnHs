package com.example.lessnon3_igor.presentation.data.responsemodel

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ResponseOrder(
    @SerializedName("number") val number: Int,
    @SerializedName("createdDelivery") val createdDelivery: Date,
)
