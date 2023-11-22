package com.example.lessnon3_igor.presentation.domain.usecase

import com.example.lessnon3_igor.presentation.data.dto.Product
import com.example.lessnon3_igor.presentation.data.repository.LessonRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: LessonRepository,
) {
        suspend fun getProducts( limit: Int, offset: Int): List<Product> {
            return repository.products(limit,offset)
        }
}