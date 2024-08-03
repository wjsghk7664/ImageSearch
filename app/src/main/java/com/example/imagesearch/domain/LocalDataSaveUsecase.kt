package com.example.imagesearch.domain

import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.storage.LocalRepository

class LocalDataSaveUsecase(private val localRepository: LocalRepository) {
    operator fun invoke(documentResponse: DocumentResponse){
        localRepository.saveData(documentResponse)
    }
}