package com.artkachenko.recipe_detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.recipe_detail.databinding.FragmentRecipeDetailBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.loadImage
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment(R.layout.fragment_recipe_detail) {

    private val viewModel by viewModels<RecipeDetailViewModel>()

    private lateinit var binding: FragmentRecipeDetailBinding

    private val argId by lazy {
        arguments?.getLong("id", 0)
    }

    private val ingredientsAdapter by lazy {
        IngredientsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailBinding.inflate(layoutInflater, container, false)

        argId?.let { viewModel.getRecipeDetail(it) }

        (activity as ImageUtils.CanHideBottomNavView).hideNavigationBar(true)

        binding.ingredients.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        binding.ingredients.adapter = ingredientsAdapter


        scope.launch {
            val details = viewModel.channel.receive()
            setupDetails(details)
        }

        return binding.root
    }

    override fun onDestroyView() {
        (activity as ImageUtils.CanHideBottomNavView).hideNavigationBar(false)

        super.onDestroyView()
    }

    private fun setupDetails(
        model: RecipeDetailModel
    ) {
        with(binding) {
            image.loadImage(model.image ?: "")
            title.text = model.title
            summary.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(model.summary, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(model.summary)
            }
            summary.movementMethod = LinkMovementMethod()
            model.diets.forEach {
                val chip = Chip(requireContext()).apply {
                    text = it
                }
                diets.addView(chip)
            }

            val ingredientsList = model.extendedIngredients

            if (!ingredientsList.isNullOrEmpty()) {
                ingredientsAdapter.submitList(ingredientsList)
            }
        }
    }
}