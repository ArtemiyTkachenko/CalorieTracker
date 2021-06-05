package com.artkachenko.core_api.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.core_api.utils.debugLog

abstract class BaseAdapter <T> (private val actions: ViewHolderActions<T> ?= null) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    protected val items = mutableListOf<T>()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(model = items[position])
    }

    override fun getItemCount() = items.size

    fun setInitial(data: List<T>, callback: (() -> Unit)? = null) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
        callback?.invoke()
    }

    fun setData(data: List<T>, callback: (() -> Unit)? = null) {
        val start = items.size
        debugLog("start is $start")
        items.addAll(data)
        debugLog("listSize is ${items.size}")
        notifyItemRangeChanged(start, start + data.size)
        callback?.invoke()
    }
}

abstract class BaseViewHolder <T>(itemView: View, private val actions: ViewHolderActions<T> ?= null) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(model: T)
}

interface ViewHolderActions<T> {

    fun onItemClicked(model: T, view: View)
}