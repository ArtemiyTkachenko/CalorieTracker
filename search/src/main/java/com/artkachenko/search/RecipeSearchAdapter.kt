package com.artkachenko.search

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.transform.CircleCropTransformation
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.search.databinding.ISearchRecipeBinding
import com.artkachenko.ui_utils.*
import java.util.*

class RecipeSearchAdapter(private val actions: RecipeSearchActions) : BaseAdapter<RecipeEntity>(actions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RecipeEntity> {
        val binding = ISearchRecipeBinding.inflate(parent.inflater(), parent, false)
        return RecipeListViewHolder(binding, actions)
    }
}

class RecipeListViewHolder(private val binding: ISearchRecipeBinding, private val actions: RecipeSearchActions) : BaseViewHolder<RecipeEntity>(binding.root, actions) {
    override fun bind(model: RecipeEntity) {
        with(binding) {
            val context = itemView.context
            val url = ImageUtils.buildRecipeImageUrl(model.id)
            recipeImage.loadCircleImage(url)
            recipeTitle.text = model.title
            recipeCookingTime.text = String.format(context.getString(R.string.cooking_time), "${model.readyInMinutes} MINS")
            recipeHealthScore.text = String.format(context.getString(R.string.health_score), model.healthScore.toString())
            recipeScore.text = String.format(context.getString(R.string.score), model.spoonacularScore.toString())
            clickContainer.setSingleClickListener {
                recipeImage.transitionName = UUID.randomUUID().toString()
                actions.onItemClicked(model, recipeImage)
            }
        }
    }
}