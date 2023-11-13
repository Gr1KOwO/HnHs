package com.example.lessnon3_igor.presentation.data.dto

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("data")val data:List<Product>
)
