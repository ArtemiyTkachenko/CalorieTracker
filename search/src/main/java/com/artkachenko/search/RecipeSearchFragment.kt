package com.artkachenko.search

import android.os.Bundle
import android.view.View
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.search.databinding.FragmentSearchBinding

class RecipeSearchFragment: BaseFragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
    }

}