package com.kaushal.galleryapp.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class RecyclerViewGridSpacing(private val space: Int) : ItemDecoration() {
    fun getItemOffset(
        outRect: Rect,
        view: View?,
        parent: RecyclerView,
        state: RecyclerView.State?
    ) {
        try {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space
            // Add top margin only for the first item to avoid double space between items
            if (view?.let { parent.getChildLayoutPosition(it) } == 0) {
                outRect.top = space
            } else {
                outRect.top = 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}