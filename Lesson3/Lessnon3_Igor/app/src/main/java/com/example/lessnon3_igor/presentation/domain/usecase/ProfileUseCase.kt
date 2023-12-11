package com.example.lessnon3_igor.presentation.domain.usecase

import com.example.lessnon3_igor.presentation.data.repository.LessonRepository
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProfile
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: LessonRepository,
) {

    suspend fun execute():ResponseProfile
    {
        return repository.getProfile()
    }
}