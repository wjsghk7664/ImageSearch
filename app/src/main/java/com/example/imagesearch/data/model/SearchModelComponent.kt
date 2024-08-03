package com.example.imagesearch.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


data class MetaResponse(
    val total_count:Int,
    val pageable_count:Int,
    val is_end:Boolean
)

interface DocumentResponse {
    val title:String?
    val thumbnail:String?
    val time: String?
}

@Serializable
data class ImageDocumentResponse(
    @SerializedName("display_sitename") override val title: String?,
    @SerializedName("thumbnail_url") override val thumbnail: String?,
    @SerializedName("datetime") override val time: String?,
):DocumentResponse

@Serializable
data class VideoDocumentResponse(
    @SerializedName("title") override val title: String?,
    @SerializedName("thumbnail") override val thumbnail: String?,
    @SerializedName("datetime") override val time: String?,
):DocumentResponse

fun ArrayList<DocumentResponse>.containsDoc(documentResponse: DocumentResponse):Boolean{
    for(i in this){
        if(i.title==documentResponse.title&&i.time==documentResponse.time&&i.thumbnail==documentResponse.thumbnail){
            return true
        }
    }
    return false
}

fun ArrayList<DocumentResponse>.indexOfDoc(documentResponse: DocumentResponse):Int{
    for((idx,i) in this.withIndex()){
        if(i.title==documentResponse.title&&i.time==documentResponse.time&&i.thumbnail==documentResponse.thumbnail){
            return idx
        }
    }
    return -1
}