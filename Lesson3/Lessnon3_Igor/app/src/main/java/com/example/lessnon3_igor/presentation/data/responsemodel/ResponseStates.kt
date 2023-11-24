package com.example.lessnon3_igor.presentation.data.responsemodel

sealed class ResponseStates<out T>() {
    class Loading<T>() : ResponseStates<T>()

    class Success<T>(val data: T) : ResponseStates<T>()

    class Failure<T>(val e: Exception) : ResponseStates<T>()
}