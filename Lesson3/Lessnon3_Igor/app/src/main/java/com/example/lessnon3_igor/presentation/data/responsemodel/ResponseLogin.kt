package com.example.lessnon3_igor.presentation.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("profile") val profile: ResponseProfile,
)