package com.example.imagesearch.data.remote

import com.example.imagesearch.data.model.ImageSearchModel
import com.example.imagesearch.data.model.VideoSearchModel
import retrofit2.Retrofit
import javax.inject.Inject


class SearchRepositoryRemoteImpl @Inject constructor(
    private val searchImage: SearchImage,
    private val searchVideo: SearchVideo
) : SearchRepository {

    override suspend fun getImage(query: String, page: Int): Result<ImageSearchModel> {
        return runCatching {
            searchImage.getSearchImage(query = query, page = page)
        }
    }

    override suspend fun getVideo(query: String, page: Int): Result<VideoSearchModel> {
        return runCatching {
            searchVideo.getSearchVideo(query = query, page = page)
        }
    }


}