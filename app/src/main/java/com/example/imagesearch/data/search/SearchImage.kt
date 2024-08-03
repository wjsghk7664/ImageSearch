package com.example.imagesearch.data.search

import com.example.imagesearch.data.model.ImageSearchModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchImage {
    @GET("/v2/search/image")
    suspend fun getSearchImage(
        @Query("query") query: String,
        @Query("sort") sort:String = "recency",
        @Query("page") page: Int
    ): ImageSearchModel
}