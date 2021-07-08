package com.artkachenko.recipe_list.recipe_list

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.core_impl.network.Filters
import com.artkachenko.recipe_list.R
import com.artkachenko.recipe_list.databinding.FragmentRecipeListBinding
import com.artkachenko.ui_utils.*
import com.artkachenko.ui_utils.themes.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : BaseFragment(R.layout.fragment_recipe_list), RecipeListActions {

    @Inject
    lateinit var themeManager: ThemeManager

    private val viewModel by viewModels<RecipeListViewModel>()

    private var binding by viewBinding<FragmentRecipeListBinding>()

    private val firstAdapter = RecipesAdapter(this).apply {
        setData(generatePlaceholderList())
    }

    private val secondAdapter = RecipesAdapter(this).apply {
        setData(generatePlaceholderList())
    }

    private val thirdAdapter = RecipesAdapter(this).apply {
        setData(generatePlaceholderList())
    }

    private val fourthAdapter = RecipesAdapter(this).apply {
        setData(generatePlaceholderList())
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

        with(binding) {
            val actions = this@RecipeListFragment
            indian.adapter = ConcatAdapter(
                firstAdapter,
                ShowMoreAdapter(actions, FilterWrapper(Filters.indianPreset.toMutableMap()))
            )
            vegetarian.adapter = ConcatAdapter(
                secondAdapter,
                ShowMoreAdapter(actions, FilterWrapper(Filters.vegetarianPreset.toMutableMap()))
            )
            italian.adapter = ConcatAdapter(
                thirdAdapter,
                ShowMoreAdapter(actions, FilterWrapper(Filters.italianPreset.toMutableMap()))
            )
            quick.adapter = ConcatAdapter(
                fourthAdapter,
                ShowMoreAdapter(actions, FilterWrapper(Filters.quickPreset.toMutableMap()))
            )
        }

        binding.search.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            .setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.clearFocus()
                    navigateToSearch(null)
                }
            }

        binding.searchViewContainer.setSingleClickListener {
            navigateToSearch(null)
        }
        viewModel.getRecipeList()
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong(ID, model.id)
            putString(TRANSITION_NAME, view.transitionName)
        }

        val extras = FragmentNavigatorExtras(view to view.transitionName)

        findNavController().navigate(R.id.recipe_to_detail, bundle, null, extras)
    }

    override fun navigateToSearch(filters: FilterWrapper?) {
        val bundle = Bundle().apply {
            putParcelable(PRESETS, filters)
        }
        val extras = FragmentNavigatorExtras(binding.searchViewContainer to "searchViewContainer")

        findNavController().navigate(R.id.recipe_to_search, bundle, null, extras)
    }

    private fun processState(state: RecipeListViewModel.State) {
        when (state) {
            RecipeListViewModel.State.FirstItemEmitted -> {
            }
            is RecipeListViewModel.State.Indian -> firstAdapter.setInitial(state.data)

            RecipeListViewModel.State.Initial -> {

            }
            is RecipeListViewModel.State.Italian -> thirdAdapter.setInitial(state.data)
            RecipeListViewModel.State.Loading -> {
            }
            is RecipeListViewModel.State.Quick -> fourthAdapter.setInitial(state.data)
            is RecipeListViewModel.State.Vegetarian -> secondAdapter.setInitial(state.data)
            else -> {
            }
        }

    }

    private fun generatePlaceholderList(): List<RecipeEntity> {
        return List(5) { index: Int -> RecipeEntity() }
    }
}