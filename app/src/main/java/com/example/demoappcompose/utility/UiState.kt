package com.example.demoappcompose.utility

sealed class UiState<out T> {
    object Empty : UiState<Nothing>()

    object Loading : UiState<Nothing>()

    data class Success<out T>(val data: T) : UiState<T>()

    data class Error(val errorMessage: String?) : UiState<Nothing>()
    data class UnAuthorised(val errorMessage: String?) : UiState<Nothing>()
}
