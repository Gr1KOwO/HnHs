package com.example.lessnon3_igor.presentation.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseProductDetails
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val product: GetProductUseCase,
) : ViewModel()
{
    private val _productState = MutableLiveData<ResponseStates<ResponseProductDetails>>()
    val productState: LiveData<ResponseStates<ResponseProductDetails>> = _productState
    private val _selectedImageItemIndex = MutableStateFlow(0)
    val selectedImageItemIndex: StateFlow<Int> get() = _selectedImageItemIndex

    fun getProduct(id: String) = viewModelScope.launch {
        _productState.value = ResponseStates.Loading()
        try {
            _productState.value = ResponseStates.Success(product.execute(id))
        } catch (e: Exception) {
            _productState.value = ResponseStates.Failure(e)
        }
    }

    fun selectImageItem(index: Int) {
        _selectedImageItemIndex.value = index
    }
}