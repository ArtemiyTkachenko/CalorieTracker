package com.artkachenko.recipe_list.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.recipe_list.R
import com.artkachenko.recipe_list.databinding.FragmentRecipeListBinding
import com.artkachenko.ui_utils.setSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment : BaseFragment(R.layout.fragment_recipe_list), RecipeListActions {

    private val viewModel by viewModels<RecipeListViewModel>()

    private lateinit var binding: FragmentRecipeListBinding

    private val firstAdapter by lazy {
        RecipesAdapter(this)
    }

    private val secondAdapter by lazy {
        RecipesAdapter(this)
    }

    private val thirdAdapter by lazy {
        RecipesAdapter(this)
    }

    private val fourthAdapter by lazy {
        RecipesAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeListBinding.bind(view)
        scope.launch {
            viewModel.recipes.collect {
                debugLog("from fragment, results are $it")
                processState(it)
            }
        }

        with (binding) {
            indian.adapter = firstAdapter
            vegetarian.adapter = secondAdapter
            italian.adapter = thirdAdapter
            quick.adapter = fourthAdapter
        }

        binding.search.setSingleClickListener {
            findNavController().navigate(R.id.recipe_to_search)
        }
//        viewModel.getRecipeList()
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong("id", model.id)
        }
        findNavController().navigate(R.id.recipe_to_detail, bundle)
    }

    private fun processState(state: RecipeListViewModel.State) {
        when (state) {
            RecipeListViewModel.State.FirstItemEmitted -> {
            }
            is RecipeListViewModel.State.Indian -> firstAdapter.setData(state.data)

            RecipeListViewModel.State.Initial -> {

            }
            is RecipeListViewModel.State.Italian -> secondAdapter.setData(state.data)
            RecipeListViewModel.State.Loading -> {

            }
            is RecipeListViewModel.State.Quick -> thirdAdapter.setData(state.data)
            is RecipeListViewModel.State.Vegetarian -> fourthAdapter.setData(state.data)
            else -> {}
        }

    }
}