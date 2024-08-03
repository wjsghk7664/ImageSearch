package com.example.imagesearch.domain

import com.example.imagesearch.data.search.SearchRepository
import com.example.imagesearch.data.model.ImageSearchModel

class RemoteImageUsecase(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String,page:Int):ImageSearchModel?{
        return repository.getImage(query,page).getOrNull()
    }
}