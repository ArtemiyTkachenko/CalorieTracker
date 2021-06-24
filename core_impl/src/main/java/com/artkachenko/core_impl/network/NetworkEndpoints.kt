package com.artkachenko.core_impl.network

object NetworkEndpoints {

    private const val baseUrl = "https://api.spoonacular.com"

    const val RecipeSearch = "$baseUrl/recipes/complexSearch"

    const val ConvertIngredients = "$baseUrl/recipes/convert"

    fun getRecipeDetailEndPoint(id: Long): String {
        return "$baseUrl/recipes/$id/information"
    }
}