package com.example.imagesearch.domain

import com.example.imagesearch.data.local.LocalRepository

class LocalDataSaveQueryUsecase(private val localRepository: LocalRepository) {
    operator fun invoke(query:String){
        localRepository.saveQuery(query)
    }
}