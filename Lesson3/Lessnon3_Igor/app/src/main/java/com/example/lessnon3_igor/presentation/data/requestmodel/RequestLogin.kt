package com.example.lessnon3_igor.presentation.data.requestmodel

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)