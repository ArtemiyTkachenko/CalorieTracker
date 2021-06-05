package com.artkachenko.search

import com.artkachenko.core_api.base.ViewHolderActions
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity

interface RecipeSearchActions: ViewHolderActions<RecipeEntity> {

    fun filterChecked(filter: Map.Entry<String, String>, isChecked: Boolean)
}