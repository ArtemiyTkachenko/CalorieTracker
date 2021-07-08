package com.artkachenko.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.FilterItemWrapper
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.FilterPair
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.search.databinding.FragmentSearchBinding
import com.artkachenko.ui_utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import viewBinding

@AndroidEntryPoint
class RecipeSearchFragment : BaseFragment(R.layout.fragment_search), RecipeSearchActions {

    private val viewModel by activityViewModels<RecipeSearchViewModel>()

    private var binding by viewBinding<FragmentSearchBinding>()

    private var queryChangeJob: Job? = null

    private val searchAdapter = RecipeSearchAdapter(this)

    private var firstLaunch = true

    private val argPresets by lazy {
        arguments?.getParcelable<FilterWrapper>(PRESETS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        sharedElementEnterTransition = DetailTransition()

        sharedElementReturnTransition = DetailTransition()

        startPostponedEnterTransition()

        binding = FragmentSearchBinding.bind(view)

        binding.arrowBack.setOnClickListener {
            activity?.onBackPressed()
        }

        AnimationUtils.animateAlpha(binding.arrowBack, shouldShow = true, delay = 300)

        argPresets?.let { viewModel.setFilter(it) }

        setAdapters()

        launchScope()

        setQueryListener()

        processArgs()
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong(ID, model.id)
            putString(TRANSITION_NAME, view.transitionName)
        }

        val extras = FragmentNavigatorExtras(view to "recipeImage")

        findNavController().navigate(R.id.search_to_detail, bundle, null, extras)
    }

    override fun onDestroy() {
        viewModelStore.clear()
        super.onDestroy()
    }

    private fun setAdapters() {
        with(binding) {
            results.adapter = searchAdapter

            results.onLoadMore {
                viewModel.loadRecipes(binding.search.query.toString())
            }
        }
    }

    private fun launchScope() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.collect {
                debugLog("searchFragment state is $it")
                processState(it)
            }
        }
    }

    private fun processState(state: RecipeSearchViewModel.State) {
        when (state) {
            RecipeSearchViewModel.State.Initial -> {
            }
            RecipeSearchViewModel.State.Loading -> {
            }
            RecipeSearchViewModel.State.LoadingFinished -> binding.progress.isVisible = false
            is RecipeSearchViewModel.State.Success -> {
                searchAdapter.setData(state.data)
            }
            RecipeSearchViewModel.State.FirstLoad -> binding.progress.isVisible = true
            RecipeSearchViewModel.State.FiltersSet -> {
                populateChips(viewModel.filtersWrapper)
                setInitial()
            }
        }
    }

    private fun setQueryListener() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                queryChangeJob?.cancel()
                queryChangeJob = lifecycleScope.launch {
                    delay(1000L)
                    if (!newText.isNullOrEmpty()) {
                        setInitial(newText)
                    }
                }

                return false
            }
        })
        binding.filter.setSingleClickListener {
            hideKeyboard()
            val fragment = RecipeFilterBottomSheet.newInstance(viewModel.filtersWrapper)
            fragment.show(parentFragmentManager, "filter_dialog")
        }
    }

    private fun processArgs() {
        argPresets?.let { filter ->
            if (firstLaunch) {
                firstLaunch = false
                populateChips(filter)
                viewModel.loadRecipes("", filter)
            }
        } ?: binding.root.postDelayed(
            {
//                binding.search.requestFocus()
//                showKeyboard()
            }, 200
        )
    }

    private fun updateFilter() {
        debugLog("updateFilter called")
        with(binding) {
            filterChips.removeAllViews()
            populateChips(viewModel.filtersWrapper)
            hideKeyboard()
            setInitial()
        }
    }

    private fun setInitial(query: String = binding.search.query.toString()) {
        searchAdapter.setInitial(emptyList())

        viewModel.getInitial(query)
    }

    private fun populateChips(filterWrapper: FilterWrapper?) {
        debugLog("populateChips called")
        val filterChips = binding.filterChips
        val filters = filterWrapper?.filters
        if (filters.isNullOrEmpty())
        filters?.forEach { filter ->
            filter.value.forEach { filterValue ->
                buildChip(
                    requireContext(),
                    filterChips,
                    isChecked = true,
                    filterValue = FilterPair(filter.key to filterValue),
                    checkCallback = { entry: Map.Entry<String, FilterItemWrapper>?, isChecked: Boolean ->
                        if (!isChecked) entry?.let { viewModel.processFilter(it) }
                        updateFilter()
                    },
                    closeCallback = { entry: Map.Entry<String, FilterItemWrapper>?, isChecked: Boolean ->
                        if (!isChecked) entry?.let { viewModel.processFilter(it) }
                        updateFilter()
                    },
                )
            }
        }
    }
}