package com.example.imagesearch.data.storage

import android.content.Context
import android.util.Log
import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.model.ImageDocumentResponse
import com.example.imagesearch.data.model.VideoDocumentResponse
import com.google.gson.Gson

class LocalDataSource(context: Context) {

    companion object{
        var key=ArrayList<String>()
    }

    private val sharedPreferences = context.getSharedPreferences("search_pref",0)

    init {
        key = sharedPreferences.all.keys.toMutableList() as ArrayList<String>
    }

    private val gson = Gson()

    fun saveData(documentResponse: DocumentResponse){
        val curkey=(if(documentResponse is ImageDocumentResponse) "I" else "V") + documentResponse.hashCode().toString()
        val docJson=gson.toJson(documentResponse)
        sharedPreferences.edit().putString(curkey,docJson).apply()
        key +=curkey
    }

    fun deleteData(position: Int){
        sharedPreferences.edit().remove(key[position]).apply()
        key.removeAt(position)
    }

    fun getDatas():ArrayList<DocumentResponse>{
        val result = ArrayList<DocumentResponse>()
        for(i in key){
            if(i[0]=='I'){
                result+=gson.fromJson(sharedPreferences.getString(i,""),ImageDocumentResponse::class.java)
            }else{
                result+=gson.fromJson(sharedPreferences.getString(i,""), VideoDocumentResponse::class.java)
            }
        }
        Log.d("로컬 데이터",result.toString())
        return result
    }
}