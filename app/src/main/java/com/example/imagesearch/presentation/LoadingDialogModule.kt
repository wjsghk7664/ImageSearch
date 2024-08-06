package com.example.imagesearch.presentation

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.imagesearch.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext


@Module
@InstallIn(ActivityComponent::class)
object LoadingDialogModule {

    @Provides
    fun provideLoadingDialog(@ActivityContext context: Context):AlertDialog{
        return AlertDialog.Builder(context).apply {
            setTitle("로딩중...")
            setView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null))
        }.create()
    }
}