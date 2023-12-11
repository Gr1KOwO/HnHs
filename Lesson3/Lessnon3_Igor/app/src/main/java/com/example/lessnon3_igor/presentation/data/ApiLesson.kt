package com.example.lessnon3_igor.presentation.data

import com.example.lessnon3_igor.presentation.data.dto.Product
import com.example.lessnon3_igor.presentation.data.requestmodel.RequestLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.BaseResponse
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductDetails
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
    ): BaseResponse<List<Product>>

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: String,
    ):BaseResponse<ResponseProductDetails>
}