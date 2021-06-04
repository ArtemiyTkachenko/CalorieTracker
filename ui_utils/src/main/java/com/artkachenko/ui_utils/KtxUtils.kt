package com.artkachenko.ui_utils

import android.content.Context
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun Context.dp(value: Float): Int {
    return (this.resources.displayMetrics.density * value).toInt()
}

fun Context.dpF(value: Float): Float {
    return (this.resources.displayMetrics.density * value)
}

fun View.dp(value: Float): Int {
    return this.context.dp(value)
}

fun View.dp(value: Int): Int {
    return this.context.dp(value.toFloat())
}

fun View.dpF(value: Float): Float {
    return this.context.dpF(value)
}

fun RecyclerView.onLoadMore(threshold: Int = 5, loadMore: () -> Unit) {
    val layoutManager = this.layoutManager as LinearLayoutManager
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int, dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            if (totalItemCount <= lastVisibleItem + threshold) {
                loadMore()
            }
        }
    })
}