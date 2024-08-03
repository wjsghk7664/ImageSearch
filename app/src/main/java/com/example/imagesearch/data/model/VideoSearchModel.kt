package com.example.imagesearch.data.model

data class VideoSearchModel(
    val meta: MetaResponse?,
    val documents: List<VideoDocumentResponse>?
)
