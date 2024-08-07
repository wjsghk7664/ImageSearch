package com.example.imagesearch.domain

import com.example.imagesearch.data.local.LocalRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LocalDataDeleteUsecase @Inject constructor(private val localRepository: LocalRepository) {
    operator fun invoke(position:Int){
        localRepository.deleteData(position)
    }
}