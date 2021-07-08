package com.artkachenko.recipe_detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.recipe_detail.databinding.IIngredientBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.loadImage

class IngredientsAdapter : BaseAdapter<Ingredient>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Ingredient> {
        val binding = IIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }
}

class IngredientViewHolder(private val binding: IIngredientBinding) : BaseViewHolder<Ingredient>(binding.root) {

    @SuppressLint("SetTextI18n")
    override fun bind(model: Ingredient) {
        with(binding) {
            debugLog("")
            val url = ImageUtils.buildIngredientsImageUrl(model.image)
            image.loadImage(url)
            name.text = model.originalString
//            amount.text = "${model.amount} ${model.unitShort}"
        }
    }
}