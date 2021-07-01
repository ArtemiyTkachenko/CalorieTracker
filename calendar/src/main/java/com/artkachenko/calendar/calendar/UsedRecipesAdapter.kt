package com.artkachenko.calendar.calendar

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.calendar.databinding.ISearchRecipeBinding
import com.artkachenko.calendar.databinding.IUsedReceiptsHeaderBinding
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.ui_utils.*
import java.util.*

class UsedRecipesAdapter(private val actions: CalendarActions) :
   RecyclerView.Adapter<UsedRecipeHeaderViewHolder>() {

    private val data = mutableListOf<RecipeEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsedRecipeHeaderViewHolder {
        val binding = IUsedReceiptsHeaderBinding.inflate(parent.inflater(), parent, false)
        return UsedRecipeHeaderViewHolder(binding, actions)
    }

    override fun onBindViewHolder(holder: UsedRecipeHeaderViewHolder, position: Int) {
        holder.bind(data)
    }

    override fun getItemCount() = if (data.isNullOrEmpty()) 0 else 1

    fun setData(recipeList: List<RecipeEntity>) {
        debugLog("USEDRECIPE, model beforeClear is $recipeList")
        data.clear()
        data.addAll(recipeList)
        debugLog("USEDRECIPE, model afterClear is $recipeList")
        notifyItemInserted(0)
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }
}

class UsedRecipeHeaderViewHolder(
    private val binding: IUsedReceiptsHeaderBinding,
    private val actions: CalendarActions
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: List<RecipeEntity>) {
        with (binding) {
            debugLog("USEDRECIPE, model is $model")
            val recipesAdapter = UsedRecipesItemAdapter(actions)
            usedRecipes.adapter = recipesAdapter
            recipesAdapter.setInitial(model)
        }
    }
}


class UsedRecipesItemAdapter(private val actions: CalendarActions) :
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
    private val actions: CalendarActions
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
}