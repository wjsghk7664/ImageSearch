package com.example.imagesearch.domain

import com.example.imagesearch.data.local.LocalRepository
import javax.inject.Inject

class LocalDataSaveQueryUsecase @Inject constructor(private val localRepository: LocalRepository) {
    operator fun invoke(query:String){
        localRepository.saveQuery(query)
    }
}