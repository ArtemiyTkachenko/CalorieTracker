package com.artkachenko.search

interface RecipeFilterActions {

    fun filterChecked(filter: Map.Entry<String, String>, isChecked: Boolean)
}