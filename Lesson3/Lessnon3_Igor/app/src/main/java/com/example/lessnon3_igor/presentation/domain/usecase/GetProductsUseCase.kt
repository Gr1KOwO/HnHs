package com.example.lessnon3_igor.presentation.domain.usecase

import com.example.lessnon3_igor.presentation.data.dto.Products
import com.example.lessnon3_igor.presentation.data.repository.LessonRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: LessonRepository,
) {
        suspend fun getProducts( limit: Int, offset: Int): Products {
            return repository.products(limit,offset)
        }
}