package com.artkachenko.core_api.base

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.core_api.network.models.HasId

abstract class BaseAdapter <T : HasId> (private val bindings: ViewHolderBindings<T> ?= null) : ListAdapter<T, BaseViewHolder<T>>(GenericDiffCallback<T>()) {

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(model = getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

abstract class BaseViewHolder <T : HasId>(itemView: View, private val bindings: ViewHolderBindings<T> ?= null) : RecyclerView.ViewHolder(itemView) {

    open fun bind(model: T) {}
}

class GenericDiffCallback <T> : DiffUtil.ItemCallback<T> () where T : Any, T : HasId {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}

interface ViewHolderBindings<T> {

    fun onItemClicked(model: T, view: View)
}