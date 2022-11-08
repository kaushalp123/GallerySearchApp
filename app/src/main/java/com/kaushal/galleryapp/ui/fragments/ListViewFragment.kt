package com.kaushal.galleryapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ListViewCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaushal.galleryapp.R
import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.data.model.ImageResponse
import com.kaushal.galleryapp.databinding.FragmentListViewBinding
import com.kaushal.galleryapp.ui.MainActivity
import com.kaushal.galleryapp.ui.adapters.ImagesListRecyclerAdapter
import com.kaushal.galleryapp.ui.MainActivityViewModel
import com.kaushal.galleryapp.util.Outcome
import kotlinx.coroutines.launch

class ListViewFragment : Fragment() {

    private  lateinit var viewModel: MainActivityViewModel
    private lateinit var imageAdapter: ImagesListRecyclerAdapter
    private lateinit var binding: FragmentListViewBinding
    private lateinit var imagesList : List<Data>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListViewBinding.bind(view)

        viewModel= (activity as MainActivity).viewModel
        initRecyclerView()
        populateRecyclerView()

    }

    private fun initRecyclerView() {
        imageAdapter = ImagesListRecyclerAdapter()
        binding.imagesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = imageAdapter
        }
    }


    private fun populateRecyclerView() {
        lifecycleScope.launch {
            viewModel.searchedResult.collect {
                when (it) {
                    is Outcome.Success ->
                        if(it.isEmptyList) { clearResult() }
                     else validateImages(it.data.data)

                    is Outcome.Empty ->  clearResult()
                    is Outcome.Failure -> Toast.makeText(requireContext(), it.errMsg, Toast.LENGTH_SHORT).show()
                    else -> Log.i("error","error fetching images")
                }
            }
        }
    }

    private fun validateImages(dataList: List<Data>) {
        for (item in dataList.indices) {
                for (subItem in dataList[item].images.indices)
                    if (dataList[item].images[subItem].type?.let { isAnImage(it) } == true)
                        imagesList = dataList
                imageAdapter.differ.submitList(dataList)
        }
    }

    private fun isAnImage(type: String) : Boolean {
        return (type == "image/jpeg") || (type == "image/png")
    }

    private fun clearResult(){
        imagesList = emptyList()
        Toast.makeText(requireContext(), "No images found for this search", Toast.LENGTH_SHORT).show()
        imageAdapter.differ.submitList(imagesList)
    }

}