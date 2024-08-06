package com.example.imagesearch.domain

import com.example.imagesearch.data.local.LocalRepository
import javax.inject.Inject

class LocalDataDeleteUsecase @Inject constructor(private val localRepository: LocalRepository) {
    operator fun invoke(position:Int){
        localRepository.deleteData(position)
    }
}