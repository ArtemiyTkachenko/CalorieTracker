package com.artkachenko.ui_utils

import android.content.Context
import android.graphics.drawable.ColorStateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
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

fun View.inflater(): LayoutInflater {
    return LayoutInflater.from(this.context)
}

fun View.setSingleClickListener(waitMillis: Long = 1000, listener: () -> Unit) {
    var lastClickTime = 0L
    setOnClickListener { view ->
        if (System.currentTimeMillis() > lastClickTime + waitMillis) {
            listener.invoke()
            lastClickTime = System.currentTimeMillis()
        }
    }
}

fun buildChip(
    context: Context,
    viewGroup: ViewGroup,
    id: Int ?= null,
    filterValue: Map.Entry<String, String>? = null,
    canClose: Boolean = true,
    checkCallback: ((Map.Entry<String, String>?, Boolean) -> Unit) ?= null
): Chip {
    return Chip(context).apply {
        id?.let { this.id = it }
        text = filterValue?.value

        if (canClose) {
            isCloseIconVisible = true
            closeIconSize = dpF(16F)
            setCloseIconResource(R.drawable.ic_baseline_close_24)
            setOnCloseIconClickListener {
                viewGroup.removeView(it)
                filterValue?.let { value -> checkCallback?.invoke(value, false) }
            }
        }

        isCheckable = true
        isClickable = true

        chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.background_color_chip_state_list)
        setTextColor(ContextCompat.getColorStateList(context, R.color.text_color_chip_state_list))

        setOnCheckedChangeListener { buttonView, isChecked ->
            filterValue?.let { value -> checkCallback?.invoke(value, isChecked) }
        }
        viewGroup.addView(this)
    }
}

fun buildChip(
    context: Context,
    viewGroup: ConstraintHelper,
    id: Int ?= null,
    filterValue: Map.Entry<String, String>? = null,
    canClose: Boolean = true,
    checkCallback: ((Map.Entry<String, String>?, Boolean) -> Unit) ?= null
): Chip {
    return Chip(context).apply {
        id?.let { this.id = it }
        text = filterValue?.value

        if (canClose) {
            isCloseIconVisible = true
            closeIconSize = dpF(16F)
            setCloseIconResource(R.drawable.ic_baseline_close_24)
            setOnCloseIconClickListener {
                viewGroup.removeView(it)
                filterValue?.let { value -> checkCallback?.invoke(value, false) }
            }
        }

        setOnCheckedChangeListener { buttonView, isChecked ->
            filterValue?.let { value -> checkCallback?.invoke(value, isChecked) }
        }
        viewGroup.addView(this)
    }
}