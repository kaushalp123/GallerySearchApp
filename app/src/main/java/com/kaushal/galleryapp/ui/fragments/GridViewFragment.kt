package com.kaushal.galleryapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kaushal.galleryapp.R
import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.databinding.FragmentGridViewBinding
import com.kaushal.galleryapp.ui.activities.MainActivity
import com.kaushal.galleryapp.ui.MainActivityViewModel
import com.kaushal.galleryapp.ui.adapters.ImagesGridRecyclerAdapter
import com.kaushal.galleryapp.util.Outcome
import com.kaushal.galleryapp.util.RecyclerViewGridSpacing
import kotlinx.coroutines.launch

class GridViewFragment : Fragment() {

    private  lateinit var viewModel: MainActivityViewModel
    private lateinit var imageAdapter: ImagesGridRecyclerAdapter
    private lateinit var binding: FragmentGridViewBinding
    private lateinit var imagesList : List<Data>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGridViewBinding.bind(view)

        viewModel= (activity as MainActivity).viewModel
        initRecyclerView()
        populateRecyclerView()

    }

    private fun initRecyclerView() {
        imageAdapter = ImagesGridRecyclerAdapter()
        binding.imagesGridRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            addItemDecoration(RecyclerViewGridSpacing(6))
            adapter = imageAdapter
        }
    }


    private fun populateRecyclerView() {
        lifecycleScope.launch {
            viewModel.searchedResult.collect() {
                when (it) {
                    is Outcome.Success ->  if(it.isEmptyList) { clearResult() }
                    else validateImages(it.data.data)
                    is Outcome.Empty ->  clearResult()
                    is Outcome.Failure -> Toast.makeText(requireContext(), it.errMsg, Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(requireContext(), "Error fetching images", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateImages(dataList: List<Data>){
        for (item in dataList.indices) {
            for (subItem in dataList[item].images.indices)
                if(dataList[item].images[subItem].type?.let { isAnImage(it) } == true)
                    imagesList = dataList

            // sorting list in reverse chronological order i.e displaying the oldest images at first.
            val newList = dataList.sortedBy { it.datetime }
            imageAdapter.differ.submitList(newList)
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