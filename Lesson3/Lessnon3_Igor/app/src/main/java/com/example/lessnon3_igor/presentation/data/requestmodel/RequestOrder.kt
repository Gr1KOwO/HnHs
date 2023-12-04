package com.example.lessnon3_igor.presentation.data.requestmodel

import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseOrderProduct
import com.google.gson.annotations.SerializedName
import java.util.Date

data class RequestOrder(
    @SerializedName("House") val house:String,
    @SerializedName("Apartment")val apartment:String,
    @SerializedName("DateDelivery")val dateDelivery: Date,
    @SerializedName("Products")val products:List<ResponseOrderProduct>
)