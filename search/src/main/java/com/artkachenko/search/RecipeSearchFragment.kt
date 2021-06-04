package com.artkachenko.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.search.databinding.FragmentSearchBinding
import com.artkachenko.ui_utils.onLoadMore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RecipeSearchFragment : BaseFragment(R.layout.fragment_search), RecipeSearchActions {

    private val viewModel by viewModels<RecipeSearchViewModel>()

    private lateinit var binding: FragmentSearchBinding

    private var queryChangeJob: Job? = null

    private val adapter = RecipeSearchAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong("id", model.id)
        }
        findNavController().navigate(R.id.search_to_detail, bundle)
    }
}