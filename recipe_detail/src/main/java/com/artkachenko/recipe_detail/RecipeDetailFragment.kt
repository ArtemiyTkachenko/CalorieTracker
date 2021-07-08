package com.artkachenko.recipe_detail

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.IngredientTitles
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.recipe_detail.databinding.FragmentRecipeDetailBinding
import com.artkachenko.ui_utils.*
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import viewBinding

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment(R.layout.fragment_recipe_detail) {

    private val viewModel by viewModels<RecipeDetailViewModel>()

    private var binding by viewBinding<FragmentRecipeDetailBinding>()

    private val argId by lazy {
        arguments?.getLong(ID, 0)
    }

    private val argTransitionName by lazy {
        arguments?.getString(TRANSITION_NAME)
    }

    private val ingredientsAdapter by lazy {
        IngredientsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeDetailBinding.bind(view)

        binding.image.transitionName = argTransitionName

        postponeEnterTransition()

        sharedElementEnterTransition = DetailTransition()

        sharedElementReturnTransition = DetailTransition()

        startPostponedEnterTransition()

        with (binding) {
            backArrow.setOnClickListener {
                activity?.onBackPressed()
            }

            argId?.let { viewModel.getRecipeDetail(it) }

            ingredients.layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            ingredients.adapter = ingredientsAdapter
        }

        scope.launch {
            val details = viewModel.channel.receive()
            setupDetails(details)
        }

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

            val calorieAmount = model.nutrition?.nutrients?.firstOrNull { it.title == IngredientTitles.CALORIES.title }?.amount?.toInt()?.toString()

            calories.text = String.format(getString(R.string.current_calories_amount), calorieAmount)

            servings.text = String.format(getString(R.string.current_serving_amount), model.servings.toString())

            val ingredientsList = model.extendedIngredients

            if (!ingredientsList.isNullOrEmpty()) {
                ingredientsAdapter.setData(ingredientsList)
            }

            add.setOnClickListener {
                val nutrients = model.nutrition
                val serving = model.servings ?: 0
                val choiceItems = mutableListOf<String>()

                (1..serving).map {
                    choiceItems.add(it.toString())
                }
                debugLog("nutrients are $nutrients")

                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.choose_serving_amount))
                    .setSingleChoiceItems(choiceItems.toTypedArray(), 1) { dialog, int ->
                        val chosenSize = choiceItems.get(int).toInt()
                        viewModel.saveRecipe(model, chosenSize)
                        Toast.makeText(requireContext(), R.string.nutrition_added, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }.show()
            }
        }
    }
}