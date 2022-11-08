package com.kaushal.galleryapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaushal.galleryapp.R
import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.data.repoImpl.SearchImageRepositoryImpl
import com.kaushal.galleryapp.databinding.ActivityMainBinding
import com.kaushal.galleryapp.ui.adapters.ImagesListRecyclerAdapter
import com.kaushal.galleryapp.util.APIErrorHandler
import com.kaushal.galleryapp.util.Outcome
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MenuProvider {

    private lateinit var binding: ActivityMainBinding

    private var isListViewSelected: Boolean = true
    private lateinit var menu: Menu

    lateinit var navController: NavController

    companion object {
        const val CLIENT_ID = "79698a75685bee3";
    }

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addMenuProvider(this)

        viewModel= ViewModelProvider(this, MainActivityViewModelFactory(
            SearchImageRepositoryImpl(),
            APIErrorHandler()
        ))[MainActivityViewModel::class.java]

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.btnSearch.setOnClickListener {
            fetchImages(binding.searchView.query?.toString())
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                fetchImages(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun fetchImages(query: String?) {
        try {
            lifecycleScope.launch {
                viewModel.fetchImages(CLIENT_ID, query, 1) // fetch images by passing client id and default page no 1.
                currentFocus?.let { hideKeyboard(it) }
            }
        } catch (ex: Exception) {
            Toast.makeText(applicationContext, ex.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_switch_view -> {
                // switching between the grid view and list view fragments on click of icon.
                if (isListViewSelected) {
                    isListViewSelected = false
                    navController.navigate(R.id.grid)
                    menu.getItem(0).icon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_list, null
                    )
                } else {
                    isListViewSelected = true
                    navController.navigate(R.id.list)
                    menu.getItem(0).icon = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_grid, null
                    )
                }
                return true
            }
        }
        return false
    }

    // hide keyboard when search is made.
    private fun hideKeyboard(view: View) {
        view?.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}