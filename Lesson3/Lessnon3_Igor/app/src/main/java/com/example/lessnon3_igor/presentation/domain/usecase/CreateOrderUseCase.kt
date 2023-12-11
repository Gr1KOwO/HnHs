package com.example.lessnon3_igor.presentation.domain.usecase

import com.example.lessnon3_igor.presentation.data.repository.LessonRepository
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseOrder
import java.util.Date
import javax.inject.Inject

class CreateOrderUseCase@Inject constructor(
    private val lessonRepository: LessonRepository,
) {
suspend fun executeOrder( id: String,house: String, apartment: String, dateDelivery: Date, size: String, quantity: Int):ResponseOrder{
    return lessonRepository.createOrder(id,house,apartment,dateDelivery,size,quantity)
}
}