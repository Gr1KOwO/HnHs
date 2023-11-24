package com.example.lessnon3_igor.presentation.data.dto

import com.google.gson.annotations.SerializedName

data class ProductSize(
    @SerializedName("value") val value: String,
    @SerializedName("isAvailable") val isAvailable: Boolean
)
