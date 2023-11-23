package com.example.lessnon3_igor.presentation.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessnon3_igor.presentation.data.dto.Product
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.data.storage.ProductDB
import com.example.lessnon3_igor.presentation.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase,
    private val productDB: ProductDB
) : ViewModel() {
    private val _exampleLiveData = MutableLiveData<ResponseStates<List<Product>>>()
    val exampleLiveData: LiveData<ResponseStates<List<Product>>> = _exampleLiveData

    fun getProducts( limit: Int, offset: Int) {
        viewModelScope.launch {
            _exampleLiveData.value = ResponseStates.Loading()
            try {
                val localProducts = productDB.getProductDao().getProducts()
                if (localProducts.isNotEmpty()) {
                    _exampleLiveData.value = ResponseStates.Success(localProducts)
                } else {
                    val products = productsUseCase.getProducts(limit, offset)
                    productDB.getProductDao().addProducts(products)
                    _exampleLiveData.value = ResponseStates.Success(products)
                }
            } catch (e: Exception) {
                _exampleLiveData.value = ResponseStates.Failure(e)
            }
        }
    }
}