package com.artkachenko.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.MapPair
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.search.databinding.FragmentSearchBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.buildChip
import com.artkachenko.ui_utils.onLoadMore
import com.artkachenko.ui_utils.setSingleClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RecipeSearchFragment : BaseFragment(R.layout.fragment_search), RecipeSearchActions {

    private val viewModel by viewModels<RecipeSearchViewModel>()

    private lateinit var binding: FragmentSearchBinding

    private var queryChangeJob: Job? = null

    private val searchAdapter = RecipeSearchAdapter(this)

    private val filterAdapter = FilterAdapter(this)

    private val argPresets by lazy {
        arguments?.getParcelable<FilterWrapper>("presets")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        viewModel

        setAdapters()

        launchScope()

        setQueryListener()

        processArgs()

        setBottomSheetBehaviour()
    }

    override fun onResume() {
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(false)
        super.onResume()
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong("id", model.id)
        }
        findNavController().navigate(R.id.search_to_detail, bundle)
    }

    override fun filterChecked(filter: Map.Entry<String, String>, isChecked: Boolean) {
        viewModel.processFilter(filter, isChecked)
    }

    private fun setAdapters() {
        with(binding) {
            results.adapter = searchAdapter

            results.onLoadMore {
                viewModel.loadRecipes(binding.search.query.toString())
            }
            filters.adapter = filterAdapter
        }
    }

    private fun launchScope() {
        scope.launch {
            viewModel.state.collect {
                debugLog("searchFragment state is $it")
                processState(it)
            }
        }
    }

    private fun processState(state: RecipeSearchViewModel.State) {
        when (state) {
            RecipeSearchViewModel.State.Initial -> { }
            RecipeSearchViewModel.State.Loading -> binding.progress.isVisible = true
            RecipeSearchViewModel.State.LoadingFinished -> binding.progress.isVisible = false
            is RecipeSearchViewModel.State.Success -> {
                searchAdapter.setData(state.data) {
                    debugLog("adapter count is ${searchAdapter.itemCount}")
                }
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
                    newText?.let { setInitial(it) }
                }

                return false
            }
        })
        binding.filter.setSingleClickListener {
            val behavior = BottomSheetBehavior.from(binding.standardBottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun processArgs() {
        argPresets?.let {
            populateChips(it)
            viewModel.loadRecipes("", it)
        } ?: binding.root.postDelayed(
            {
//                binding.search.requestFocus()
//                showKeyboard()
            }, 200
        )
    }

    private fun setBottomSheetBehaviour() {
        with(binding) {
            val behavior = BottomSheetBehavior.from(standardBottomSheet)
            standardBottomSheet.setOnClickListener {
                behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        apply.isVisible = newState == BottomSheetBehavior.STATE_EXPANDED
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                }
                )
                if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            apply.setSingleClickListener {
                updateFilter()
            }
        }
    }

    private fun updateFilter() {
        with (binding) {
            filterChips.removeAllViews()
            populateChips(viewModel.filtersWrapper)
            val behavior = BottomSheetBehavior.from(standardBottomSheet)
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            hideKeyboard()
            setInitial()
        }
    }

    private fun setInitial(query: String = binding.search.query.toString()) {
        searchAdapter.setInitial(emptyList()) {
            debugLog("adapter count is ${searchAdapter.itemCount}")
        }
        viewModel.getInitial(query)
    }

    private fun populateChips(filterWrapper: FilterWrapper?) {
        val filterChips = binding.filterChips
        filterWrapper?.filters?.forEach { filter ->
            filter.value.forEach { filterValue ->
                buildChip(
                    requireContext(),
                    filterChips,
                    isChecked = true,
                    filterValue = MapPair(filter.key to filterValue),
                    checkCallback = { entry: Map.Entry<String, String>?, isChecked: Boolean ->
                        if (!isChecked) entry?.let { viewModel.processFilter(it, isChecked) }
                        updateFilter()
                    }
                )
            }
        }
    }
}