package com.artkachenko.ui_utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation

object ImageUtils {

    private const val baseUrl = "https://spoonacular.com/recipeImages/"
    private const val baseIngredientsUrl = "https://spoonacular.com/cdn/ingredients_100x100/"

    enum class RecipeImageSize(val size: String) {
        XS("90x90"),
        S("240x150"),
        XM("312x150"),
        M("312x231"),
        L("480x360"),
        XL("556x370"),
        XXL("636x393")
    }

    fun buildRecipeImageUrl(id: Long, imageSize: RecipeImageSize = RecipeImageSize.XM, type: String = ".jpg"): String {
        return "$baseUrl$id-${imageSize.size}$type"
    }

    fun buildIngredientsImageUrl(image: String?): String {
        return "$baseIngredientsUrl$image"
    }

    interface CanHideBottomNavView {
        fun hideNavigationBar(show : Boolean)
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

fun ImageView.loadCircleImage(@DrawableRes drawable: Int, @ColorInt backgroundColor: Int = Color.TRANSPARENT) {
    this.load(drawable) {
        scale(Scale.FIT)
        transformations(CircleCropTransformation())
//        setBackgroundColor(backgroundColor)
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