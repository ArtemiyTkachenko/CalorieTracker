package com.artkachenko.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
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
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(false)

        binding = FragmentSearchBinding.bind(view)

        viewModel

        setAdapters()

        launchScope()

        setQueryListener()

        processArgs()

        setBottomSheetBehaviour()
    }

    override fun onDestroyView() {
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(true)
        super.onDestroyView()
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong("id", model.id)
        }
        findNavController().navigate(R.id.search_to_detail, bundle)
    }

    override fun filterChecked(filter: Map.Entry<String, String>, isChecked: Boolean) {

    }

    private fun setAdapters() {
        with (binding) {
            results.adapter = searchAdapter

            results.onLoadMore {
                viewModel.loadRecipes(binding.search.query.toString())
            }
            filters.adapter = filterAdapter
        }

    }

    private fun launchScope() {
        scope.launch {
            viewModel.results.collect {
                searchAdapter.setData(it) {
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
                    newText?.let { viewModel.getInitial(it) }
                }

                return false
            }
        })
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
        binding.standardBottomSheet.setOnClickListener {
            val behavior = BottomSheetBehavior.from(it)
            if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun populateChips(filterWrapper: FilterWrapper) {
        val filterChips = binding.filterChips
        filterChips
        filterWrapper.filters.forEach { filter ->
            filter.value.forEach { filterValue ->
                buildChip(requireContext(), filterChips, filterValue = MapPair(filter.key to filterValue))
            }
        }
    }
}