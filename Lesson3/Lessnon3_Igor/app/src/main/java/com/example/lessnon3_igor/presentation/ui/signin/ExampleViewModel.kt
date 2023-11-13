package com.example.lessnon3_igor.presentation.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseLogin
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.domain.usecase.LoginUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch

class ExampleViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _exampleLiveData = MutableLiveData<ResponseStates<ResponseLogin>>()
    val exampleLiveData: LiveData<ResponseStates<ResponseLogin>> = _exampleLiveData

    fun login(email:String, password:String) {
        viewModelScope.launch {
            _exampleLiveData.value = ResponseStates.Loading()
            try {
                _exampleLiveData.value = ResponseStates.Success(
                    loginUseCase.execute(email, password)

                )
            } catch (e: Exception) {
                _exampleLiveData.value = ResponseStates.Failure(
                    e
                )
            }
        }
    }
}