package com.artkachenko.ui_utils

import android.view.View
import android.widget.ImageView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation

object ImageUtils {

    private const val baseUrl = "https://spoonacular.com/recipeImages/"
    private const val baseIngredientsUrl = "https://spoonacular.com/cdn/ingredients_100x100/"

    fun buildRecipeImageUrl(id: Long, size: String = "312x150", type: String = ".jpg"): String {
        return "$baseUrl$id-$size$type"
    }

    fun buildIngredientsImageUrl(image: String?): String {
        return "$baseIngredientsUrl$image"
    }
}

fun ImageView.loadImage(url: String) {
    this.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_my_recipes_placeholder_image)
//        transformations(CircleCropTransformation())
        scale(Scale.FIT)
    }
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