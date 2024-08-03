package com.example.imagesearch.data.storage

import com.example.imagesearch.data.model.DocumentResponse

interface LocalRepository {
    fun getDatas():ArrayList<DocumentResponse>
    fun deleteData(position:Int)
    fun saveData(documentResponse: DocumentResponse)
}