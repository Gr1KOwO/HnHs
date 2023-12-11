package com.example.lessnon3_igor.presentation.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ResponseBadge(
    @SerializedName("value") val value: String,
    @SerializedName("color") val color: String
)
