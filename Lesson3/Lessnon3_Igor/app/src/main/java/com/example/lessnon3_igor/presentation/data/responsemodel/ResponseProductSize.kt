package com.example.lessnon3_igor.presentation.data.responsemodel

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ResponseProductSize(
    @SerializedName("value") val value: String,
    @SerializedName("isAvailable") val isAvailable: Boolean
):Parcelable
