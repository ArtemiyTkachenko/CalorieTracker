package com.artkachenko.calendar.calendar

import android.view.ViewGroup
import com.artkachenko.calendar.databinding.IBarItemBinding
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.ui_utils.inflater

class SourcesAdapter : BaseAdapter<Map<String, Double>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Map<String, Double>> {
        val binding = IBarItemBinding.inflate(parent.inflater(), parent, false)
        return SourcesViewHolder(binding)
    }
}

class SourcesViewHolder(private val binding: IBarItemBinding): BaseViewHolder<Map<String, Double>>(binding.root) {
    override fun bind(model: Map<String, Double>) {
        with (binding) {
            binding.sourcesBarChart.setData(model.values.toList())
        }
    }
}