package com.artkachenko.recipe_list.recipe_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.recipe_list.databinding.IRecipeListBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.loadImage
import com.artkachenko.ui_utils.setSingleClickListener

class RecipesAdapter(private val bindings: RecipeListActions) : BaseAdapter<RecipeEntity>(bindings) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RecipeEntity> {
        val binding = IRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding, bindings)
    }
}

class RecipeListViewHolder(private val binding: IRecipeListBinding, private val viewHolderBindings: RecipeListActions) : BaseViewHolder<RecipeEntity>(binding.root, viewHolderBindings) {
    override fun bind(model: RecipeEntity) {
        with(binding) {
            val url = ImageUtils.buildRecipeImageUrl(model.id)
            recipeImage.loadImage(url)
            recipeTitle.text = model.title
            root.setSingleClickListener {
                viewHolderBindings.onItemClicked(model, recipeImage)
            }
        }
    }
}