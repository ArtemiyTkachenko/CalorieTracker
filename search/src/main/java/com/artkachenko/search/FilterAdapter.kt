package com.artkachenko.search

import android.view.ViewGroup
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.MapPair
import com.artkachenko.core_impl.network.Filters
import com.artkachenko.search.databinding.IFilterGroupBinding
import com.artkachenko.ui_utils.buildChip
import com.artkachenko.ui_utils.inflater

class FilterAdapter(private val actions: RecipeSearchActions) : BaseAdapter<FilterWrapper>() {

    init {
        setInitial(
            listOf(
                FilterWrapper(Filters.cuisineFilters),
                FilterWrapper(Filters.dietFilters),
                FilterWrapper(Filters.intolerancesFilters)
            )
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<FilterWrapper> {
        val binding = IFilterGroupBinding.inflate(parent.inflater(), parent, false)
        return FilterViewHolder(binding, actions)
    }
}

class FilterViewHolder(
    private val binding: IFilterGroupBinding,
    private val actions: RecipeSearchActions
) : BaseViewHolder<FilterWrapper>(binding.root) {

    override fun bind(model: FilterWrapper) {
        with(binding) {
            model.filters.forEach { filter ->
                filterTitle.text = filter.key.capitalize()
                filter.value.forEach { filterValue ->
                    buildChip(
                        itemView.context,
                        filters,
                        canClose = false,
                        filterValue = MapPair(filter.key to filterValue),
                        checkCallback = { entry, isChecked ->
                            entry?.let {
                                actions.filterChecked(
                                    entry,
                                    isChecked
                                )
                            }
                        })
                }
            }
        }
    }
}