package com.example.imagesearch.data.local

import com.example.imagesearch.data.model.DocumentResponse

class LocalRepositoryImpl(private val localDataSource: LocalDataSource):LocalRepository {
    override fun getDatas(): ArrayList<DocumentResponse> {
        return localDataSource.getDatas()
    }

    override fun deleteData(position: Int) {
        localDataSource.deleteData(position)
    }

    override fun saveData(documentResponse: DocumentResponse) {
        localDataSource.saveData(documentResponse)
    }

    override fun saveQuery(query: String) {
        localDataSource.saveQuery(query)
    }

    override fun getQuery(): String {
        return localDataSource.getQuery()
    }

}