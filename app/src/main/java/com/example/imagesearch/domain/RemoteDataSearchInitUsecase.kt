package com.example.imagesearch.domain

import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.search.SearchRepository

class RemoteDataSearchInitUsecase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(query:String):ArrayList<DocumentResponse>?{
        val page = 1
        val img=searchRepository.getImage(query, page).getOrNull()
        val video=searchRepository.getVideo(query, page).getOrNull()

        var result:List<DocumentResponse>?

        if(img==null&&video==null){
            result=null
        }else{
            result=((img?.documents?: listOf()) + (video?.documents?: listOf())).sortedByDescending { it.time }
        }
        return result?.toCollection(ArrayList())

    }
}