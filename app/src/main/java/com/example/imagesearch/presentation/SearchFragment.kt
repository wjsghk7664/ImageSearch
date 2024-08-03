package com.example.imagesearch.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.data.search.SearchRepositoryRemoteImpl
import com.example.imagesearch.data.model.DocumentResponse
import com.example.imagesearch.data.storage.LocalDataSource
import com.example.imagesearch.data.storage.LocalRepositoryImpl
import com.example.imagesearch.databinding.FragmentSearchBinding
import com.example.imagesearch.presentation.viewmodel.SearchViewModel
import com.example.imagesearch.presentation.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels{
        SearchViewModelFactory(SearchRepositoryRemoteImpl(),LocalRepositoryImpl(LocalDataSource(requireContext())))
    }

    private var list = ArrayList<DocumentResponse>()

    private lateinit var searchAdapter: SearchAdapter
    private var page = 1
    private var query = ""

    private var _binding :FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    fun initView() = with(binding){

        searchAdapter= SearchAdapter(onClickAdd, onClickDelete)
        searchRv.adapter = searchAdapter
        searchRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.results.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Empty -> list = ArrayList()
                is UiState.Failure -> {
                    if (it.page == 1) list = ArrayList()
                    page = 1
                }

                is UiState.Success -> if (it.page == 1) list = it.docuList else list += it.docuList
                is UiState.Update -> searchAdapter.updateStored(it.stored)
                is UiState.Loading -> null
            }
            searchAdapter.submitList(list.toList())
        }

        searchRv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(searchRv.canScrollVertically(-1)){
                    if(searchFab.visibility==View.INVISIBLE){
                        searchFab.visibility = View.VISIBLE
                        ObjectAnimator.ofFloat(searchFab, "alpha", 0f,1f).apply {
                            duration=400
                        }.start()
                    }
                }else{
                    ObjectAnimator.ofFloat(searchFab,"alpha",1f,0f).apply {
                        duration=200
                    }.start()
                    lifecycleScope.launch {
                        delay(200)
                        searchFab.visibility=View.INVISIBLE
                    }
                }

                if(!searchRv.canScrollVertically(1)){
                    if(query.isNotEmpty()) viewModel.search(query,++page)
                }
            }
        })

        searchFab.setOnClickListener {
            searchRv.smoothScrollToPosition(0)
        }


        searchBtn.setOnClickListener {
            query=searchEt.text.toString()
            if(query.isEmpty()){
                searchAdapter.submitList(listOf())
            }
            else{
                page=1
                viewModel.search(query,page)
            }
        }
    }

    val onClickAdd:(DocumentResponse) ->Unit = { documentResponse ->
        viewModel.addData(documentResponse)
    }

    val onClickDelete:(Int) -> Unit = { position ->
        viewModel.deleteData(position)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}