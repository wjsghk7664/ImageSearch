package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.domain.LocalDataDeleteUsecase
import com.example.imagesearch.domain.LocalDataGetQueryUsecase
import com.example.imagesearch.domain.LocalDataGetUsecase
import com.example.imagesearch.domain.LocalDataSaveQueryUsecase
import com.example.imagesearch.domain.LocalDataSaveUsecase
import com.example.imagesearch.domain.RemoteImageUsecase
import com.example.imagesearch.domain.RemoteVideoUsecase
import com.example.imagesearch.presentation.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val remoteImageUsecase: RemoteImageUsecase,
    private val remoteVideoUsecase: RemoteVideoUsecase,
    private val localDataGetUsecase: LocalDataGetUsecase,
    private val localDataDeleteUsecase: LocalDataDeleteUsecase,
    private val localDataSaveUsecase: LocalDataSaveUsecase,
    private val localDataGetQueryUsecase: LocalDataGetQueryUsecase,
    private val localDataSaveQueryUsecase: LocalDataSaveQueryUsecase
) : ViewModel() {

    private val _results: MutableLiveData<UiState> = MutableLiveData(UiState.Empty)
    val results = _results as LiveData<UiState>

    init {
        initQuery()
    }

    fun search(query: String, page:Int){
        var list:ArrayList<DocumentResponse>? = ArrayList()
        updateData()
        viewModelScope.launch {
            _results.value= UiState.Loading

            val ImageResult = remoteImageUsecase(query, page)
            val VideoResult = remoteVideoUsecase(query, page)

            if(ImageResult==null&&VideoResult==null){
                list = null
            }else{
                list = ArrayList()
                ImageResult?.documents?.forEach { list!!+=it }
                VideoResult?.documents?.forEach { list!!+=it }
                list!!.sortByDescending{it.time}
            }
        }.invokeOnCompletion {
            _results.value=when(list){
                ArrayList<DocumentResponse>() -> UiState.Empty
                null -> UiState.Failure(page)
                else -> UiState.Success(list as ArrayList<DocumentResponse>, page)
            }
         }
    }

    fun addData(documentResponse: DocumentResponse){
        localDataSaveUsecase(documentResponse)
        updateData()
    }

    fun deleteData(position: Int){
        localDataDeleteUsecase(position)
        updateData()
    }

    fun updateData(){
        _results.value = UiState.Update(localDataGetUsecase())
    }

    fun initQuery(){
        _results.value = UiState.Init(localDataGetQueryUsecase(),localDataGetUsecase())
    }

    fun saveQuery(query:String){
        localDataSaveQueryUsecase(query)
    }


}