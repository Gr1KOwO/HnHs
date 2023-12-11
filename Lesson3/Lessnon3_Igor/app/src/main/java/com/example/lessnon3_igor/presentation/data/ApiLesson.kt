package com.example.lessnon3_igor.presentation.data

import com.example.lessnon3_igor.presentation.data.dto.Product
import com.example.lessnon3_igor.presentation.data.requestmodel.RequestLogin
import com.example.lessnon3_igor.presentation.data.requestmodel.RequestOrder
import com.example.lessnon3_igor.presentation.data.responsemodel.BaseResponse
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseOrder
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductDetails
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("orders/checkout")
    suspend fun createOrder(
        @Body order: RequestOrder,
    ):BaseResponse<ResponseOrder>

    @GET("user")
    suspend fun getProfileData():BaseResponse<ResponseProfile>
}