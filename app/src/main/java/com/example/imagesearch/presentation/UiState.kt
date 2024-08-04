package com.example.imagesearch.presentation

import com.example.imagesearch.data.model.DocumentResponse
import retrofit2.http.Query

sealed class UiState {
    data class Init(val initQuery: String,val stored:ArrayList<DocumentResponse>) : UiState()
    object Empty : UiState()
    object AdditionalEmpty:UiState()
    object Loading : UiState()
    object FailureInit: UiState()
    object FailureAdditional : UiState() //
    data class SuccessInit(val docuList: ArrayList<DocumentResponse>) : UiState()
    data class SuccessAdditional(val docuList:ArrayList<DocumentResponse>): UiState()
    data class Update(val stored:ArrayList<DocumentResponse>): UiState()
}