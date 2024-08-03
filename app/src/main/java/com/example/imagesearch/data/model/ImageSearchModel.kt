package com.example.imagesearch.data.model

data class ImageSearchModel(
    val meta: MetaResponse?,
    val documents: List<ImageDocumentResponse>?
)
