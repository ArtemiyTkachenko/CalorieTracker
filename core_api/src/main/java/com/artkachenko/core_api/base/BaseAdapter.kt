package com.artkachenko.core_api.base

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.core_api.network.models.HasId

abstract class BaseAdapter <T : HasId> (private val bindings: ViewHolderBindings<T> ?= null) : RecyclerView.Adapter<BaseViewHolder<T>>() {

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
        items.addAll(data)
        notifyItemRangeChanged(start, start + data.size - 1)
        callback?.invoke()
    }
}

abstract class BaseViewHolder <T : HasId>(itemView: View, private val bindings: ViewHolderBindings<T> ?= null) : RecyclerView.ViewHolder(itemView) {

    open fun bind(model: T) {}
}

interface ViewHolderBindings<T> {

    fun onItemClicked(model: T, view: View)
}