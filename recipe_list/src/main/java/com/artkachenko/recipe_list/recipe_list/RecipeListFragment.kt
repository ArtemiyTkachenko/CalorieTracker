package com.artkachenko.recipe_list.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.artkachenko.recipe_list.R
import com.artkachenko.recipe_list.databinding.FragmentRecipeListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private val viewModel by viewModels<RecipeListViewModel>()
//    @Inject
//    lateinit var viewModel: RecipeListViewModel

    private lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textDashboard.text = it
        })

        viewModel.getRecipeList()

        return binding.root
    }
}