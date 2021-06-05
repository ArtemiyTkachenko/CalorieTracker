package com.artkachenko.recipe_list.recipe_list

import com.artkachenko.core_api.base.ViewHolderActions
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity

interface RecipeListActions : ViewHolderActions<RecipeEntity> {

    fun navigateToSearch(filters: FilterWrapper?)
}