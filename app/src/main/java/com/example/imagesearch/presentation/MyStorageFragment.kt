package com.example.imagesearch.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.data.remote.SearchRepositoryRemoteImpl
import com.example.imagesearch.data.local.LocalDataSource
import com.example.imagesearch.data.local.LocalRepositoryImpl
import com.example.imagesearch.databinding.FragmentMyStorageBinding
import com.example.imagesearch.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyStorageFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()

    private lateinit var storageAdapter: StorageAdapter

    private var _binding: FragmentMyStorageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyStorageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("뷰모델 체크",viewModel.hashCode().toString())

        initView()
    }

    fun initView() = with(binding){
        storageAdapter = StorageAdapter(onClick)
        mystorageRv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        mystorageRv.adapter=storageAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.results.collectLatest {
                Log.d("컬 스토리지",it.toString())
                when (it) {
                    is UiState.Update -> storageAdapter.submitList(it.stored.toList())
                    else -> null
                }
            }
        }

    }

    val onClick:(Int) -> Unit = { position ->
        viewModel.deleteData(position)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyStorageFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}