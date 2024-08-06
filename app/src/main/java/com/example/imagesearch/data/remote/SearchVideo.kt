package com.example.imagesearch.data.remote

import com.example.imagesearch.data.model.VideoSearchModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchVideo {
    @GET("/v2/search/vclip")
    suspend fun getSearchVideo(
        @Query("query") query: String,
        @Query("sort") sort:String = "recency",
        @Query("page") page: Int
    ): VideoSearchModel
}