package com.artkachenko.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.search.databinding.FragmentSearchBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.dpF
import com.artkachenko.ui_utils.onLoadMore
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RecipeSearchFragment : BaseFragment(R.layout.fragment_search), RecipeSearchActions {

    private val viewModel by viewModels<RecipeSearchViewModel>()

    private lateinit var binding: FragmentSearchBinding

    private var queryChangeJob: Job? = null

    private val adapter = RecipeSearchAdapter(this)

    private val argPresets by lazy {
        arguments?.getParcelable<FilterWrapper>("presets")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(false)

        binding = FragmentSearchBinding.bind(view)

        binding.results.adapter = adapter

        viewModel

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

        binding.results.onLoadMore {
            viewModel.loadRecipes(binding.search.query.toString())
        }

        scope.launch {
            viewModel.results.collect {
                adapter.setData(it) {
                    debugLog("adapter count is ${adapter.itemCount}")
                }
            }
        }

        val map = mapOf<String, List<String>>()

        argPresets?.let {
            populateChips(it)
            viewModel.loadRecipes("", it)
        } ?: binding.root.postDelayed(
            {
                binding.search.requestFocus()
                showKeyboard()
            }, 200
        )

        binding.standardBottomSheet.setOnClickListener {
            val behavior = BottomSheetBehavior.from(it)
            if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
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

    private fun populateChips(filterWrapper: FilterWrapper) {
        val filterChips = binding.filterChips
        filterWrapper.filters.forEach { filter ->
            filter.second.forEach { filterValue ->
                Chip(requireContext()).apply {
                    text = filterValue

                    isCloseIconVisible = true
                    closeIconSize = dpF(16F)
                    setCloseIconResource(R.drawable.ic_baseline_close_24)
                    setOnCloseIconClickListener {
                        filterChips.removeView(it)
                    }
                    filterChips.addView(this)
                }
            }
        }
    }
}