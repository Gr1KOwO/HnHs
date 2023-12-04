package com.example.lessnon3_igor.presentation.data.repository

import com.example.lessnon3_igor.presentation.data.ApiLesson
import com.example.lessnon3_igor.presentation.data.dto.Product
import com.example.lessnon3_igor.presentation.data.requestmodel.RequestLogin
import com.example.lessnon3_igor.presentation.data.requestmodel.RequestOrder
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseOrder
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseOrderProduct
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductDetails
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProfile
import com.example.lessnon3_igor.presentation.data.storage.dao.productDAO
import java.util.Date

import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson,
    private val productDAO: productDAO
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
    suspend fun getDBProducts():List<Product>
    {
        return productDAO.getProducts()
    }

    suspend fun addAllProducts(listProduct:List<Product>){
        productDAO.addProducts(listProduct)
    }

    suspend fun createOrder(id: String, house: String, apartment: String, dateDelivery: Date, size: String, quantity: Int): ResponseOrder
    {
        return apiLesson.createOrder(RequestOrder(house,apartment,dateDelivery, listOf(ResponseOrderProduct(id,size,quantity)))).data
    }

    suspend fun getProfile(): ResponseProfile
    {
        return apiLesson.getProfileData().data
    }

}