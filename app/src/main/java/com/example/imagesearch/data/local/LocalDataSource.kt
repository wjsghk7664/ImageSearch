package com.example.imagesearch.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.model.ImageDocumentResponse
import com.example.imagesearch.data.model.VideoDocumentResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class LocalDataSource @Inject constructor(
    @SharePreferencesSearch private val sharedPreferencesSearch: SharedPreferences,
    @SharedPreferencesQuary private val sharedPreferencesQuery: SharedPreferences,
    private val gson: Gson
) {

    var key = ArrayList<String>()

    init {
        key = sharedPreferencesSearch.all.keys.sorted().toMutableList() as ArrayList<String>
    }

    fun saveData(documentResponse: DocumentResponse) {
        val curkey = System.currentTimeMillis()
            .toString() + (if (documentResponse is ImageDocumentResponse) "I" else "V")
        val docJson = gson.toJson(documentResponse)
        sharedPreferencesSearch.edit().putString(curkey, docJson).apply()
        key += curkey
    }

    fun deleteData(position: Int) {
        try {
            sharedPreferencesSearch.edit().remove(key[position]).apply()
            key.removeAt(position)
        } catch (e: Exception) {
        }

    }

    fun getDatas(): ArrayList<DocumentResponse> {
        val result = ArrayList<DocumentResponse>()
        for (i in key) {
            Log.d("키", i)
            if (i.last() == 'I') {
                result += gson.fromJson(
                    sharedPreferencesSearch.getString(i, ""),
                    ImageDocumentResponse::class.java
                )
            } else {
                result += gson.fromJson(
                    sharedPreferencesSearch.getString(i, ""),
                    VideoDocumentResponse::class.java
                )
            }
        }
        Log.d("로컬 데이터", result.toString())
        return result
    }

    fun saveQuery(query: String) {
        sharedPreferencesQuery.edit().putString("query", query).apply()
    }

    fun getQuery(): String {
        return sharedPreferencesQuery.getString("query", "") ?: ""
    }
}