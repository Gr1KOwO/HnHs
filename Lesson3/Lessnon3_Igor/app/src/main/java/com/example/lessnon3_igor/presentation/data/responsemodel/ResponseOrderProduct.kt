package com.example.lessnon3_igor.presentation.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ResponseOrderProduct (
    @SerializedName("ProductId") val productId: String,
    @SerializedName("Size") val size: String,
    @SerializedName("Quantity") val quantity: Int,
)