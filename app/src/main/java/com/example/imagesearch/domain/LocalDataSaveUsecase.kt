package com.example.imagesearch.domain

import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.local.LocalRepository
import javax.inject.Inject

class LocalDataSaveUsecase @Inject constructor(private val localRepository: LocalRepository) {
    operator fun invoke(documentResponse: DocumentResponse){
        localRepository.saveData(documentResponse)
    }
}