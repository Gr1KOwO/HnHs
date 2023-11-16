package com.example.lessnon3_igor.presentation.data

import com.example.lessnon3_igor.presentation.data.dto.ProductData
import com.example.lessnon3_igor.presentation.data.dto.Products
import com.example.lessnon3_igor.presentation.data.requestmodel.RequestLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.BaseResponse
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseLogin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiLesson {

    @PUT("user/signin")
    suspend fun login(
        @Body requestLogin: RequestLogin,
    ): BaseResponse<ResponseLogin>

    @GET("products")
    suspend fun getProductList(
        @Query("PageSize") limit: Int,
        @Query("PageSize") offset: Int,
    ): Products

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: String,
    ):ProductData
}