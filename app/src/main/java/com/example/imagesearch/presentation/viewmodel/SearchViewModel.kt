package com.example.imagesearch.presentation.viewmodel

import android.util.Log
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
import com.example.imagesearch.domain.RemoteDataSearchUsecase
import com.example.imagesearch.presentation.UiState
import kotlinx.coroutines.launch

class SearchViewModel(
    private val remoteDataSearchUsecase: RemoteDataSearchUsecase,
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

    fun searchInit(query: String){
        var list:ArrayList<DocumentResponse>? = ArrayList()
        viewModelScope.launch {
            _results.value=UiState.Loading
            list=remoteDataSearchUsecase(query,1,true)
        }.invokeOnCompletion {
            _results.value=when(list){
                ArrayList<DocumentResponse>() -> UiState.Empty
                null -> UiState.FailureInit
                else -> UiState.SuccessInit(list!!)
            }
         }
    }

    fun searchAdditional(query: String, page:Int){
        var list:ArrayList<DocumentResponse>? = ArrayList()
        viewModelScope.launch {
            _results.value=UiState.Loading
            list=remoteDataSearchUsecase(query, page, false)
            Log.d("추가 검색",list.toString())
        }.invokeOnCompletion {
            _results.value=when(list){
                ArrayList<DocumentResponse>() -> UiState.AdditionalEmpty
                null -> UiState.FailureAdditional
                else -> UiState.SuccessAdditional(list!!)
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