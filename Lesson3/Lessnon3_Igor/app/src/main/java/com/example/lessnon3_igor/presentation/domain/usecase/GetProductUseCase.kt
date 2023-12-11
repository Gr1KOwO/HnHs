package com.example.lessnon3_igor.presentation.domain.usecase

import com.example.lessnon3_igor.presentation.data.repository.LessonRepository
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductDetails
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val lessonRepository: LessonRepository,
)
{
    suspend fun execute(id:String): ResponseProductDetails {
        return lessonRepository.productDetails(id)
    }
}