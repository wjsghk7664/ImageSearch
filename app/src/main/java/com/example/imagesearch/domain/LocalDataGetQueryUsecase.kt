package com.example.imagesearch.domain

import com.example.imagesearch.data.local.LocalRepository

class LocalDataGetQueryUsecase(private val localRepository: LocalRepository) {
    operator fun invoke():String{
        return localRepository.getQuery()
    }
}