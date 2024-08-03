package com.example.imagesearch.domain

import com.example.imagesearch.data.search.SearchRepository
import com.example.imagesearch.data.model.VideoSearchModel

class RemoteVideoUsecase(private val repository: SearchRepository) {
    suspend operator fun invoke(query:String, page:Int): VideoSearchModel? {
        return repository.getVideo(query,page).getOrNull()
    }
}