package com.artkachenko.search

import com.artkachenko.core_api.network.models.FilterItemWrapper

interface RecipeFilterActions {

    fun filterChecked(filter: Map.Entry<String, FilterItemWrapper>, isChecked: Boolean)
}