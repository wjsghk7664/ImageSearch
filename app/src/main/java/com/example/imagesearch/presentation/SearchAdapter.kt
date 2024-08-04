package com.example.imagesearch.presentation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.model.ImageDocumentResponse
import com.example.imagesearch.data.model.containsDoc
import com.example.imagesearch.data.model.equalsDoc
import com.example.imagesearch.data.model.indexOfDoc
import com.example.imagesearch.databinding.SearchItemBinding

class SearchAdapter(val onClickAdd:(DocumentResponse) ->Unit,val onClickDelete:(Int) ->Unit): ListAdapter<DocumentResponse, ViewHolder>(
    diffUtil
) {

    companion object{
        private var prevStored=ArrayList<DocumentResponse>()
        private var curStored=ArrayList<DocumentResponse>()

        val diffUtil = object :DiffUtil.ItemCallback<DocumentResponse>(){
            override fun areItemsTheSame(
                oldItem: DocumentResponse,
                newItem: DocumentResponse
            ): Boolean {
                return oldItem.equalsDoc(newItem)
            }

            override fun areContentsTheSame(
                oldItem: DocumentResponse,
                newItem: DocumentResponse
            ): Boolean {
                return oldItem.equalsDoc(newItem)&&(prevStored.containsDoc(oldItem)== curStored.containsDoc(newItem))
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(SearchItemBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val documentResponse:DocumentResponse = currentList[position]
        (holder as SearchViewHolder).apply {
            img.load(documentResponse.thumbnail){
                scale(Scale.FILL)
            }
            title.setText((if(documentResponse is ImageDocumentResponse) "[Image]" else "[Video]")+documentResponse.title)
            date.setText(documentResponse.time)
            star.visibility = if(curStored.containsDoc(documentResponse)) View.VISIBLE else View.GONE

            rootView.setOnClickListener{
                if(curStored.containsDoc(documentResponse)){
                    onClickDelete(curStored.indexOfDoc(documentResponse))
                }else{
                    onClickAdd(documentResponse)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun updateStored(documentResponses: ArrayList<DocumentResponse>){
        prevStored = curStored
        curStored = documentResponses
    }

    class SearchViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root){
        val rootView = binding.root
        val img = binding.itemIvImg
        val title = binding.itemTvTitle
        val date = binding.itemTvDate
        val star = binding.itemIvStar
    }



}