package com.artkachenko.ui_utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.core_api.network.models.FilterItemWrapper
import com.artkachenko.ui_utils.views.ThemeAwareChip
import com.google.android.material.chip.Chip
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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

fun RecyclerView.onLoadMore(threshold: Int = 3, loadMore: () -> Unit) {
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
    filterValue: Map.Entry<String, FilterItemWrapper>? = null,
    canClose: Boolean = true,
    isChecked: Boolean = false,
    checkCallback: ((Map.Entry<String, FilterItemWrapper>?, Boolean) -> Unit) ?= null,
    closeCallback: ((Map.Entry<String, FilterItemWrapper>?, Boolean) -> Unit) ?= null
): ThemeAwareChip {
    return ThemeAwareChip(context).apply {
        id?.let { this.id = it }
        text = filterValue?.value?.value

        if (canClose) {
            isCloseIconVisible = true
            closeIconSize = dpF(16F)
            setCloseIconResource(R.drawable.ic_baseline_close_24)
            setOnCloseIconClickListener {
                viewGroup.removeView(it)
                filterValue?.let { value ->
                    value.value.isChecked = false
                    closeCallback?.invoke(value, false) }
            }
        }

        isCheckable = true
        isClickable = true

        this.isChecked = isChecked

        setOnCheckedChangeListener { buttonView, isChecked ->
            filterValue?.let { value ->
                value.value.isChecked = !value.value.isChecked
                checkCallback?.invoke(value, isChecked) }
        }
        viewGroup.addView(this)
    }
}

fun getColorForScore(score: Int?): Int {
    if (score == null) return R.color.text_primary
    return when (score) {
        in 90..100 -> R.color.green_700
        in 80..89 -> R.color.green_500
        in 70..79 -> R.color.green_200
        in 60..69 -> R.color.yellow_200
        in 50..59 -> R.color.yellow_500
        in 40..49 -> R.color.yellow_700
        in 30..39 -> R.color.red_500
        else -> R.color.red_200
    }
}

fun buildSpan(score: Int?, context: Context, @StringRes stringRes: Int): SpannableString {
    val scoreString = String.format(context.getString(stringRes), score.toString())
    val colorStart = scoreString.indexOfFirst { it == ':' }
    val colorEnd = scoreString.indexOfFirst { it == '/' }
    val span = SpannableString(scoreString)
    span.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context, getColorForScore(score))),
        colorStart + 1,
        colorEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return span
}

