package com.artkachenko.calendar.calendar

import android.view.ViewGroup
import com.artkachenko.calendar.databinding.ICalorieProgressBinding
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.ui_utils.inflater

class ProgressChartAdapter : BaseAdapter<Pair<Long, Long>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Pair<Long, Long>> {
        val binding = ICalorieProgressBinding.inflate(parent.inflater(), parent, false)
        return ProgressChartViewHolder(binding)
    }
}

class ProgressChartViewHolder(private val binding: ICalorieProgressBinding) : BaseViewHolder<Pair<Long, Long>>(binding.root) {

    override fun bind(model: Pair<Long, Long>) {
        with (binding) {
            val desiredAmount = model.second
            val spent = model.first
            binding.progressChart.setData(spent, desiredAmount)
            binding.calorieBase.text = desiredAmount.toString()
            binding.caloriesSpent.text = "$spent"
            binding.caloriesLeft.text = "${desiredAmount - spent}"
        }
    }
}