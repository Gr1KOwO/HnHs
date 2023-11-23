package com.example.lessnon3_igor.presentation.data.repository

import com.example.lessnon3_igor.presentation.data.ApiLesson
import com.example.lessnon3_igor.presentation.data.dto.Product
import com.example.lessnon3_igor.presentation.data.requestmodel.RequestLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductDetails
import com.example.lessnon3_igor.presentation.data.storage.ProductDB
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson
) {
    suspend fun login(email: String, password: String): ResponseLogin {
        return apiLesson.login(RequestLogin(email, password)).data
    }

    suspend fun products(limit: Int,offset: Int):List<Product>
    {
        return  apiLesson.getProductList(limit, offset).data
    }
    suspend fun productDetails(id: String): ResponseProductDetails {
        return apiLesson.getProduct(id).data
    }
}