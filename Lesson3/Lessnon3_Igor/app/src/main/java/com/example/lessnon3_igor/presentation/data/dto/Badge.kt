package com.example.lessnon3_igor.presentation.data.dto

import com.google.gson.annotations.SerializedName

data class Badge(
    @SerializedName("value") val value: String,
    @SerializedName("color") val color: String
)
