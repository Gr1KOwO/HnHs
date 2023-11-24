package com.example.lessnon3_igor.presentation.data.dto

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("department") val department: String,
    @SerializedName("price") val price: Double,
    @SerializedName("preview") val preview: String
)
