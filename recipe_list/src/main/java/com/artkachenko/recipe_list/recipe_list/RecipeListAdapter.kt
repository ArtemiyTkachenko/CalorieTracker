package com.artkachenko.recipe_list.recipe_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.recipe_list.databinding.IRecipeListBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.inflater
import com.artkachenko.ui_utils.loadImage
import com.artkachenko.ui_utils.setSingleClickListener
import java.util.*

class RecipesAdapter(private val actions: RecipeListActions) : BaseAdapter<RecipeEntity>(actions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RecipeEntity> {
        val binding = IRecipeListBinding.inflate(parent.inflater(), parent, false)
        return RecipeListViewHolder(binding, actions)
    }
}

class RecipeListViewHolder(private val binding: IRecipeListBinding, private val actions: RecipeListActions) : BaseViewHolder<RecipeEntity>(binding.root, actions) {
    override fun bind(model: RecipeEntity) {
        with(binding) {
            val url = ImageUtils.buildRecipeImageUrl(model.id)
            recipeImage.loadImage(url)
            recipeTitle.text = model.title
            clickContainer.setSingleClickListener {
                recipeImage.transitionName = UUID.randomUUID().toString()
                actions.onItemClicked(model, recipeImage)
            }
        }
    }
}