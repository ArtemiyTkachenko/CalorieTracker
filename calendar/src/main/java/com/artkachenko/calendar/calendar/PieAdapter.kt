package com.artkachenko.calendar.calendar

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.artkachenko.calendar.R
import com.artkachenko.calendar.databinding.IPieChartBinding
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.ui_utils.inflater
import com.artkachenko.ui_utils.themes.ThemeManager
import com.github.mikephil.charting.utils.ColorTemplate

class PieAdapter() : BaseAdapter<Triple<Long,Long,Long>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Triple<Long,Long,Long>> {
        val binding = IPieChartBinding.inflate(parent.inflater(), parent, false)
        return PieChartHolder(binding)
    }
}

class PieChartHolder(private val binding: IPieChartBinding) :
    BaseViewHolder<Triple<Long,Long,Long>>(binding.root) {
    @SuppressLint("SetTextI18n")
    override fun bind(model: Triple<Long, Long, Long>) {
        val (fat, protein, carb) = model
        val context = binding.root.context
        with (binding) {
            pieChart.setChartData(fatRate = fat, proteinRate = protein, carbRate = carb)
            fatLegend.text = "${context.getText(R.string.fat_percentage)} $fat%"
            proteinLegend.text = "${context.getText(R.string.protein_percentage)} $protein%"
            carbsLegend.text = "${context.getText(R.string.carb_percentage)} $carb%"

        }
    }
}