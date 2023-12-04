package com.example.lessnon3_igor.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProfile
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.domain.usecase.ProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel@Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel()
{
    private val _profileLiveData = MutableLiveData<ResponseStates<ResponseProfile>>()
    val profileLiveData: LiveData<ResponseStates<ResponseProfile>> = _profileLiveData

    fun executeProfile()=
    viewModelScope.launch {
        _profileLiveData.value = ResponseStates.Loading()
        try {
            _profileLiveData.value = ResponseStates.Success(profileUseCase.execute())
        }
        catch (e:Exception)
        {
            _profileLiveData.value = ResponseStates.Failure(e)
        }
    }


}