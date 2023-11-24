package com.example.lessnon3_igor.presentation.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ErrorBaseResponse(
    @SerializedName("error") val error: ErrorErrorResponse,
)

class ErrorErrorResponse(
    @SerializedName("message") val message: String,
)