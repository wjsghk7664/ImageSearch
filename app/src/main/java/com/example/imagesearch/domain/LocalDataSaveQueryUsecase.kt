package com.example.imagesearch.domain

import com.example.imagesearch.data.storage.LocalRepository

class LocalDataSaveQueryUsecase(private val localRepository: LocalRepository) {
    operator fun invoke(query:String){
        localRepository.saveQuery(query)
    }
}