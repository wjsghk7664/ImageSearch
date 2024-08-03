package com.example.imagesearch.domain

import com.example.imagesearch.data.storage.LocalRepository

class LocalDataDeleteUsecase(private val localRepository: LocalRepository) {
    operator fun invoke(position:Int){
        localRepository.deleteData(position)
    }
}