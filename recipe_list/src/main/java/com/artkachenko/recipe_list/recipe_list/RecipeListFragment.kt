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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment : BaseFragment(R.layout.fragment_recipe_list), RecipeListActions {

    private val viewModel by viewModels<RecipeListViewModel>()

    private lateinit var binding: FragmentRecipeListBinding

    private val recipesAdapter by lazy {
        RecipesAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        scope.launch {
            viewModel.recipes.collect {
                debugLog("from fragment, results are $it")
                recipesAdapter.submitList(it)
            }
        }

        binding.recycler.adapter = recipesAdapter

        viewModel.getRecipeList()

        return binding.root
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong("id", model.id)
        }
        findNavController().navigate(R.id.recipe_to_detail, bundle)
    }
}