package com.example.lessnon3_igor.presentation.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseOrder
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.example.lessnon3_igor.presentation.domain.usecase.CreateOrderUseCase
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel()
{
    private val _orderLiveData = MutableLiveData<ResponseStates<ResponseOrder>>()
    val orderLiveData: LiveData<ResponseStates<ResponseOrder>> = _orderLiveData

    private val _quantityLiveData = MutableLiveData<Int>()
    val quantityLiveData: LiveData<Int> = _quantityLiveData
    fun getQuantity(): Int = _quantityLiveData.value ?: 1

    fun setQuantity(value: Int) {
        _quantityLiveData.value = when {
            value < 1 -> 1
            value > 10 -> 10
            else -> value
        }
    }

    fun executeOrder(id:String,house: String,apartment:String,dateDelivery:Date,size:String,quantity:Int) {
        viewModelScope.launch {
            _orderLiveData.value = ResponseStates.Loading()
            try {
                _orderLiveData.value = ResponseStates.Success(
                    createOrderUseCase.executeOrder(id,house,apartment,dateDelivery,size, quantity)
                )
            } catch (e: Exception) {
                _orderLiveData.value = ResponseStates.Failure(
                    e
                )
            }
        }
    }

}