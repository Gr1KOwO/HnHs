package com.example.lessnon3_igor.presentation.data.responsemodel

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(
    @SerializedName("data") val data: T,
)