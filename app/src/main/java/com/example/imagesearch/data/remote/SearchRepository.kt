package com.example.imagesearch.data.remote

import com.example.imagesearch.data.model.ImageSearchModel
import com.example.imagesearch.data.model.VideoSearchModel

interface SearchRepository {
    suspend fun getImage(query:String, page:Int):Result<ImageSearchModel>
    suspend fun getVideo(query:String, page:Int):Result<VideoSearchModel>
}