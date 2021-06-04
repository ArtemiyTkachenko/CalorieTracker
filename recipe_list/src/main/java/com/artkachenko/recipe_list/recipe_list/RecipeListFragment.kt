package com.artkachenko.recipe_list.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.core_impl.network.FilterPresets
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

    private val firstAdapter = RecipesAdapter(this)

    private val secondAdapter = RecipesAdapter(this)

    private val thirdAdapter = RecipesAdapter(this)

    private val fourthAdapter =  RecipesAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeListBinding.bind(view)
        scope.launch {
            viewModel.recipes.collect {
                debugLog("from fragment, results are $it")
                processState(it)
            }
        }

        with(binding) {
            val actions = this@RecipeListFragment
            indian.adapter = ConcatAdapter(
                firstAdapter,
                ShowMoreAdapter(actions, FilterWrapper(FilterPresets.indianPreset))
            )
            vegetarian.adapter = ConcatAdapter(
                secondAdapter,
                ShowMoreAdapter(actions, FilterWrapper(FilterPresets.vegetarianPreset))
            )
            italian.adapter = ConcatAdapter(
                thirdAdapter,
                ShowMoreAdapter(actions, FilterWrapper(FilterPresets.italianPreset))
            )
            quick.adapter = ConcatAdapter(
                fourthAdapter,
                ShowMoreAdapter(actions, FilterWrapper(FilterPresets.quickPreset))
            )
        }

        binding.search.setSingleClickListener {
            findNavController().navigate(R.id.recipe_to_search)
        }
        viewModel.getRecipeList()
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong("id", model.id)
        }
        findNavController().navigate(R.id.recipe_to_detail, bundle)
    }

    override fun moveToFragmentWithPresets(filters: FilterWrapper) {
        val bundle = Bundle().apply {
            putParcelable("presets", filters)
        }
        findNavController().navigate(R.id.recipe_to_search, bundle)
    }

    private fun processState(state: RecipeListViewModel.State) {
        when (state) {
            RecipeListViewModel.State.FirstItemEmitted -> {
            }
            is RecipeListViewModel.State.Indian -> firstAdapter.setInitial(state.data)

            RecipeListViewModel.State.Initial -> {

            }
            is RecipeListViewModel.State.Italian -> secondAdapter.setInitial(state.data)
            RecipeListViewModel.State.Loading -> {
            }
            is RecipeListViewModel.State.Quick -> thirdAdapter.setInitial(state.data)
            is RecipeListViewModel.State.Vegetarian -> fourthAdapter.setInitial(state.data)
            else -> {
            }
        }

    }
}