package com.kaushal.galleryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuProvider

class MainActivity : AppCompatActivity() , MenuProvider {

    private var isListViewSelected : Boolean = true
    private lateinit var menu : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.action_switch_view -> {
                if(isListViewSelected) {
                    switchViews("grid")
                    isListViewSelected = false
                    menu.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_grid, null)
                } else {
                    switchViews("list")
                    isListViewSelected = true
                    menu.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_list, null)
                }
                return true
            }
        }
        return false
    }

    private fun switchViews(type: String){


    }
}