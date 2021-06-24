package com.artkachenko.search

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.search.databinding.ISearchRecipeBinding
import com.artkachenko.ui_utils.*
import java.util.*

class RecipeSearchAdapter(private val actions: RecipeSearchActions) :
    BaseAdapter<RecipeEntity>(actions) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecipeEntity> {
        val binding = ISearchRecipeBinding.inflate(parent.inflater(), parent, false)
        return RecipeListViewHolder(binding, actions)
    }
}

class RecipeListViewHolder(
    private val binding: ISearchRecipeBinding,
    private val actions: RecipeSearchActions
) : BaseViewHolder<RecipeEntity>(binding.root, actions) {
    override fun bind(model: RecipeEntity) {
        with(binding) {
            val context = itemView.context
            val url = ImageUtils.buildRecipeImageUrl(model.id)
            recipeImage.loadCircleImage(url)
            recipeTitle.text = model.title

            recipeHealthScore.text = buildSpan(model.healthScore?.toInt(), context, R.string.health_score)

            recipeScore.text = buildSpan(model.spoonacularScore?.toInt(), context, R.string.score)

            recipeCookingTime.text =
                String.format(context.getString(R.string.cooking_time), "${model.readyInMinutes}")

            clickContainer.setSingleClickListener {
                recipeImage.transitionName = UUID.randomUUID().toString()
                actions.onItemClicked(model, recipeImage)
            }
        }
    }

    private fun buildSpan(score: Int?, context: Context, @StringRes stringRes: Int): SpannableString {
        val scoreString = String.format(context.getString(stringRes), score.toString())
        val colorStart = scoreString.indexOfFirst { it == ':' }
        val colorEnd = scoreString.indexOfFirst { it == '/' }
        val span = SpannableString(scoreString)
        span.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, getColorForScore(score))),
            colorStart + 1,
            colorEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }

    private fun getColorForScore(score: Int?): Int {
        if (score == null) return com.artkachenko.ui_utils.R.color.text_primary
        return when (score) {
            in 90..100 -> com.artkachenko.ui_utils.R.color.green_700
            in 80..89 -> com.artkachenko.ui_utils.R.color.green_500
            in 70..79 -> com.artkachenko.ui_utils.R.color.green_200
            in 60..69 -> com.artkachenko.ui_utils.R.color.yellow_200
            in 50..59 -> com.artkachenko.ui_utils.R.color.yellow_500
            in 40..49 -> com.artkachenko.ui_utils.R.color.yellow_700
            in 30..39 -> com.artkachenko.ui_utils.R.color.red_500
            else -> com.artkachenko.ui_utils.R.color.red_200
        }
    }
}