package com.example.imagesearch.data.search

import com.example.imagesearch.data.model.ImageSearchModel
import com.example.imagesearch.data.model.VideoSearchModel


class SearchRepositoryRemoteImpl: SearchRepository {

    override suspend fun getImage(query:String, page:Int):Result<ImageSearchModel>{
        return runCatching{
            RetrofitClient.searchImages.getSearchImage(query=query,page=page)
        }
    }

    override suspend fun getVideo(query:String, page:Int):Result<VideoSearchModel> {
        return runCatching {
            RetrofitClient.searchVideo.getSearchVideo(query=query,page=page)
        }
    }


}