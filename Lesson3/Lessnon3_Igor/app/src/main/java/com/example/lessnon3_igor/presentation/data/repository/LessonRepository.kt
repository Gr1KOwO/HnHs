package com.example.lessnon3_igor.presentation.data.repository

import com.example.lessnon3_igor.presentation.data.ApiLesson
import com.example.lessnon3_igor.presentation.data.dto.Products
import com.example.lessnon3_igor.presentation.data.requestmodel.RequestLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseLogin
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson,
) {
    suspend fun login(email: String, password: String): ResponseLogin {
        return apiLesson.login(RequestLogin(email, password)).data
    }

    suspend fun products(limit: String,offset: String):Products
    {
        return apiLesson.getProductList(limit,offset)
    }
}