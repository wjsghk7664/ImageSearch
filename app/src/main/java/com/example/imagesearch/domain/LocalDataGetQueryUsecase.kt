package com.example.imagesearch.domain

import com.example.imagesearch.data.local.LocalRepository
import javax.inject.Inject

class LocalDataGetQueryUsecase @Inject constructor(private val localRepository: LocalRepository) {
    operator fun invoke():String{
        return localRepository.getQuery()
    }
}