package com.example.lessnon3_igor.presentation.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("occupation") val occupation: String,
    @SerializedName("avatarId") val avatarId: String,
)