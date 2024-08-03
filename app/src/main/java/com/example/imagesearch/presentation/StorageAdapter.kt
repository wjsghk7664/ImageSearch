package com.example.imagesearch.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.model.ImageDocumentResponse
import com.example.imagesearch.data.model.indexOfDoc
import com.example.imagesearch.databinding.SearchItemBinding

class StorageAdapter(val onClick:(Int) ->Unit): ListAdapter<DocumentResponse, ViewHolder>(
    SearchAdapter.diffUtil
) {

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<DocumentResponse>(){
            override fun areItemsTheSame(
                oldItem: DocumentResponse,
                newItem: DocumentResponse
            ): Boolean {
                return (oldItem.thumbnail == newItem.thumbnail)&&(oldItem.time == newItem.time)&&(oldItem.title == newItem.title)
            }

            override fun areContentsTheSame(
                oldItem: DocumentResponse,
                newItem: DocumentResponse
            ): Boolean {
                return (oldItem.thumbnail == newItem.thumbnail)&&(oldItem.time == newItem.time)&&(oldItem.title==newItem.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StorageViewHolder(SearchItemBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val documentResponse:DocumentResponse = currentList[position]
        (holder as StorageViewHolder).apply {
            img.load(documentResponse.thumbnail){
                scale(Scale.FILL)
            }
            title.setText((if(documentResponse is ImageDocumentResponse) "[Image]" else "[Video]")+documentResponse.title)
            date.setText(documentResponse.time)

            rootView.setOnClickListener{
                onClick((ArrayList<DocumentResponse>(currentList)).indexOfDoc(documentResponse))
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class StorageViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root){
        val rootView = binding.root
        val img = binding.itemIvImg
        val title = binding.itemTvTitle
        val date = binding.itemTvDate
    }
}