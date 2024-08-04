package com.example.imagesearch.domain

import android.util.Log
import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.search.SearchRepository

class RemoteDataSearchUsecase(private val searchRepository: SearchRepository) {

    var imgIsEnd = false
    var videoIsEnd = false

    suspend operator fun invoke(query:String,page:Int):ArrayList<DocumentResponse>?{
        val img=if(!imgIsEnd) searchRepository.getImage(query, page).getOrNull() else null
        val video=if(!videoIsEnd) searchRepository.getVideo(query, page).getOrNull() else null

        imgIsEnd=img?.meta?.is_end?:true
        videoIsEnd=video?.meta?.is_end?:true

        Log.d("추가 검색 이미지",img?.toString()?:"")
        Log.d("추가 검색 비디오", video?.toString()?:"")

        var result:List<DocumentResponse>?

        if(img==null&&video==null){
            result=null
        }else{
            result=((img?.documents?: listOf()) + (video?.documents?: listOf())).sortedByDescending { it.time }
        }
        return result?.toCollection(ArrayList())

    }
}