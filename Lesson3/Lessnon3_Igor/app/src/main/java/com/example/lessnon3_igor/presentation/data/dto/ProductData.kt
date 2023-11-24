package com.example.lessnon3_igor.presentation.data.dto

import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("department") val department: String,
    @SerializedName("price") val price: Int,
    @SerializedName("badge") val badge: List<Badge>,
    @SerializedName("preview") val preview: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("sizes") val sizes: List<ProductSize>,
    @SerializedName("description") val description: String,
    @SerializedName("details") val details: List<String>
)
