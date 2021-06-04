package com.artkachenko.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.search.databinding.ISearchRecipeBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.loadImage
import com.artkachenko.ui_utils.setSingleClickListener

class RecipeSearchAdapter(private val bindings: RecipeSearchActions) : BaseAdapter<RecipeEntity>(bindings) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RecipeEntity> {
        val binding = ISearchRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding, bindings)
    }
}

class RecipeListViewHolder(private val binding: ISearchRecipeBinding, private val viewHolderBindings: RecipeSearchActions) : BaseViewHolder<RecipeEntity>(binding.root, viewHolderBindings) {
    override fun bind(model: RecipeEntity) {
        with(binding) {
            val url = com.artkachenko.ui_utils.ImageUtils.buildRecipeImageUrl(model.id)
            recipeImage.loadImage(url)
            recipeTitle.text = model.title
            root.setSingleClickListener {
                viewHolderBindings.onItemClicked(model, recipeImage)
            }
        }
    }
}