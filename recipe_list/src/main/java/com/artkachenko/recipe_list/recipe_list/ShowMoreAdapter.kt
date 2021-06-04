package com.artkachenko.recipe_list.recipe_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.recipe_list.databinding.IShowMoreBinding
import com.artkachenko.ui_utils.setSingleClickListener

class ShowMoreAdapter(private val actions: RecipeListActions, private val filters: FilterWrapper): RecyclerView.Adapter<ShowMoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowMoreViewHolder {
        val binding = IShowMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowMoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowMoreViewHolder, position: Int) {
        holder.bind(actions, filters)
    }

    override fun getItemCount() = 1
}

class ShowMoreViewHolder(private val binding: IShowMoreBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(actions: RecipeListActions, filters: FilterWrapper) {
        binding.root.setSingleClickListener {
            actions.navigateToSearch(filters)
        }
    }
}