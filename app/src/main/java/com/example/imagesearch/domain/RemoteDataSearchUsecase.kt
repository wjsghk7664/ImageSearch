package com.example.imagesearch.domain

import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.remote.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class RemoteDataSearchUsecase(private val searchRepository: SearchRepository) {

    private var imgIsEnd = false
    private var videoIsEnd = false

    suspend operator fun invoke(query:String,page:Int, isInit:Boolean):ArrayList<DocumentResponse>?{

        if(isInit) {
            imgIsEnd =false
            videoIsEnd =false
        }


        var result:List<DocumentResponse>?
        coroutineScope {
            withContext(Dispatchers.IO){
                val imgDef = async { if(!imgIsEnd) searchRepository.getImage(query, page).getOrNull() else null }
                val videoDef=async { if(!videoIsEnd) searchRepository.getVideo(query, page).getOrNull() else null }

                val img=imgDef.await()
                val video=videoDef.await()

                imgIsEnd=img?.meta?.is_end?:true
                videoIsEnd=video?.meta?.is_end?:true
                if(img==null&&video==null){
                    result=null
                }else{
                    result=((img?.documents?: listOf()) + (video?.documents?: listOf())).sortedByDescending { it.time }
                }
            }
        }

        return result?.toCollection(ArrayList())

    }
}