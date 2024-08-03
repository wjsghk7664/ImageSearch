package com.example.imagesearch.presentation

import com.example.imagesearch.data.model.DocumentResponse

sealed class UiState {
    object Empty : UiState()
    object Loading : UiState()
    data class Failure(val page:Int) : UiState() //
    data class Success(val docuList:ArrayList<DocumentResponse>, val page: Int): UiState()
    data class Update(val stored:ArrayList<DocumentResponse>): UiState()
}