package com.kaushal.galleryapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuProvider
import androidx.core.view.isNotEmpty
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaushal.galleryapp.R
import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.data.repoImpl.SearchImageRepositoryImpl
import com.kaushal.galleryapp.databinding.ActivityMainBinding
import com.kaushal.galleryapp.util.APIErrorHandler
import com.kaushal.galleryapp.util.Outcome
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Objects

class MainActivity : AppCompatActivity(), MenuProvider {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageAdapter: ImagesDataRecyclerAdapter

    private var isListViewSelected: Boolean = true
    private lateinit var menu: Menu

    companion object {
        const val CLIENT_ID = "79698a75685bee3";
    }

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            SearchImageRepositoryImpl(),
            APIErrorHandler(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addMenuProvider(this)

        binding.btnSearch.setOnClickListener {
            fetchImages(binding.searchView.query?.toString())
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fetchImages(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        initRecyclerView()
    }

    private fun switchViews(type: String) {

    }

    private fun initRecyclerView() {
        imageAdapter = ImagesDataRecyclerAdapter()
        binding.imagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = imageAdapter
        }
    }

    private fun fetchImages(query: String?) {
        try {
            lifecycleScope.launch {
                viewModel.fetchImages(CLIENT_ID, query, 1)
                
                delay(1500)
                populateRecyclerView()
            }
        } catch (ex: Exception) {
            Toast.makeText(applicationContext, ex.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateRecyclerView() {
        lifecycleScope.launch {
            viewModel.searchedResult.collect() {
                when (it) {
                    is Outcome.Success -> validateImages(it.data.data)
                    is Outcome.Empty -> Toast.makeText(applicationContext, "No images found for this search", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(applicationContext, "Error fetching images", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateImages(dataList: List<Data>){
        for (item in dataList.indices){
            for (subItem in dataList[item].images.indices)
                if(dataList[item].images[subItem].type?.let { isAnImage(it) } == true)
                    imageAdapter.differ.submitList(dataList)
        }
    }

    private fun isAnImage(type: String) : Boolean {
        return (type == "image/jpeg") || (type == "image/png")
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_switch_view -> {
                if (isListViewSelected) {
                    switchViews("grid")
                    isListViewSelected = false
                    menu.getItem(0).icon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_grid, null
                    )
                } else {
                    switchViews("list")
                    isListViewSelected = true
                    menu.getItem(0).icon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_list, null
                    )
                }
                return true
            }
        }
        return false
    }
}