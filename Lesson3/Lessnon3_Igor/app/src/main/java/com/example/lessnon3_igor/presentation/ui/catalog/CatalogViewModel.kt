package com.example.lessnon3_igor.presentation.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessnon3_igor.presentation.data.dto.Product
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase,
) : ViewModel() {
    private val _exampleLiveData = MutableLiveData<ResponseStates<List<Product>>>()
    val exampleLiveData: LiveData<ResponseStates<List<Product>>> = _exampleLiveData

    fun getProducts( limit: Int, offset: Int) {
        viewModelScope.launch {
            _exampleLiveData.value = ResponseStates.Loading()
            try {
                _exampleLiveData.value = ResponseStates.Success(
                    productsUseCase.getProducts(limit, offset)
                )
            } catch (e: Exception) {
                _exampleLiveData.value = ResponseStates.Failure(e)
            }
        }
    }
}