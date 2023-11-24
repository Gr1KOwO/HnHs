package com.example.lessnon3_igor.presentation.domain.usecase

import com.example.lessnon3_igor.presentation.data.repository.LessonRepository
import com.example.lessnon3_igor.presentation.data.repository.PreferenceStorage
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseLogin
import javax.inject.Inject
class LoginUseCase @Inject constructor(
    private val repository: LessonRepository,
    private val preferenceStorage: PreferenceStorage,
) {
    suspend fun execute(email: String, password: String): ResponseLogin {
        val loginData = repository.login(email, password)
        preferenceStorage.userToken = loginData.accessToken
        return loginData
    }
}