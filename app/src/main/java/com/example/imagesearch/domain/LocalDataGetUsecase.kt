package com.example.imagesearch.domain

import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.local.LocalRepository
import javax.inject.Inject

class LocalDataGetUsecase @Inject constructor(private val localRepository: LocalRepository) {
    operator fun invoke():ArrayList<DocumentResponse>{
        return localRepository.getDatas()
    }
}