package com.artkachenko.calendar.calendar

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.calendar.databinding.IBarChartBinding
import com.artkachenko.calendar.databinding.IDefaultChartBinding
import com.artkachenko.calendar.databinding.IPieChartBinding
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.ui_utils.inflater
import com.artkachenko.ui_utils.themes.Theme
import com.artkachenko.ui_utils.themes.ThemeManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class ChartAdapter(private val themeManager: ThemeManager) : ListAdapter<ChartDataWrapper<*>, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<ChartDataWrapper<*>>() {
    override fun areItemsTheSame(
        oldItem: ChartDataWrapper<*>,
        newItem: ChartDataWrapper<*>
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ChartDataWrapper<*>,
        newItem: ChartDataWrapper<*>
    ): Boolean {
        return oldItem == newItem
    }
}) {
    private val list = mutableListOf<ChartDataWrapper<*>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.inflater()

        return when (viewType) {
            PIE_ENTRY -> {
                val binding = IPieChartBinding.inflate(inflater, parent, false)
                PieChartHolder(binding)
            }
            BAR_ENTRY -> {
                val binding = IBarChartBinding.inflate(inflater, parent, false)
                BarChartHolder(binding, themeManager)
            }
            else -> {
                val binding = IDefaultChartBinding.inflate(inflater, parent, false)
                DefaultChartHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is PieChartHolder -> holder.bind(item as ChartDataWrapper<PieData>)
            is BarChartHolder -> holder.bind(item as ChartDataWrapper<BarData>)
            is DefaultChartHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val itemType = when (list[position].data) {
            is BarData -> BAR_ENTRY
            is BubbleData -> BUBBLE_ENTRY
            is CandleData -> CANDLE_ENTRY
            is PieData -> PIE_ENTRY
            is RadarData -> RADAR_ENTRY
            else -> UNKNOWN_ENTRY
        }
        debugLog("got here and itemtype is $itemType")
        return itemType
    }

    fun addData(data: ChartDataWrapper<*>) {
        list.add(data)
        submitList(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
        submitList(list)
        notifyDataSetChanged()
    }

    companion object {
        const val BAR_ENTRY = 0
        const val BUBBLE_ENTRY = 1
        const val CANDLE_ENTRY = 2
        const val PIE_ENTRY = 3
        const val RADAR_ENTRY = 4
        const val UNKNOWN_ENTRY = 99
    }
}

class PieChartHolder(private val binding: IPieChartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(wrapper: ChartDataWrapper<PieData>) {
        with(binding.pieChart) {
            debugLog("Got here and pie chart is $this")
            description.isEnabled = false
            holeRadius = 52f
            transparentCircleRadius = 57f
            setCenterTextSize(9f)
            setUsePercentValues(true)
            setExtraOffsets(5F, 10F, 50F, 10F)
            data = wrapper.data
            animateY(400)
        }
    }
}

class BarChartHolder(private val binding: IBarChartBinding, private val themeManager: ThemeManager) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(wrapper: ChartDataWrapper<BarData>) {
        with(binding.barChart) {
            val labels = binding.labels
            labels.removeAllViews()
            val colors = ColorTemplate.COLORFUL_COLORS
            var modifiedIndex = -1
            val stackLabels = wrapper.data.dataSets.first().stackLabels.mapIndexed { index, title ->
                LegendEntry().apply {
                    label = title
                    form = Legend.LegendForm.CIRCLE
                    if (modifiedIndex >= colors.size) modifiedIndex = 0
                    modifiedIndex = if (index >= colors.size) modifiedIndex else index
                    formColor = colors[modifiedIndex]
                    modifiedIndex++

                }
            }
            legend.setCustom(stackLabels)

            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.textColor = if (themeManager.theme == Theme.DARK) Color.WHITE else Color.BLACK
            legend.setDrawInside(false)
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            disableScroll()
            setPinchZoom(false)
//            labels.weightSum = stackLabels?.size?.toFloat() ?: 0F
//            stackLabels.forEachIndexed { index, s ->
//                val label = TextView(itemView.context).apply {
//                    layoutParams =
//                        LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
//                            .apply {
//                                val firstItemOffset =
//                                    if (index == 0 && stackLabels.size > 1) dp(10F) else 0
//                                val lastItemOffset =
//                                    if (index == stackLabels.size - 1 && stackLabels.size > 1) dp(
//                                        10F
//                                    ) else 0
//                                val margin = dp(8F)
//                                updateMargins(
//                                    left = margin + firstItemOffset,
//                                    top = margin,
//                                    bottom = margin,
//                                    right = margin + lastItemOffset
//                                )
//                                textAlignment = TEXT_ALIGNMENT_CENTER
//                                textSize = 14F
//                            }
//                    text = s
//                }
//                labels.addView(label)
//            }
            debugLog("Got here and pie chart is $this")
            setDrawGridBackground(false)
            xAxis.setDrawLabels(false)
            axisLeft.setDrawGridLines(false)
            xAxis.setDrawGridLines(false)
//            val labels = entry.dataSets.first().stackLabels

//            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
//            xAxis.valueFormatter = XAxisFormatter(labels)
//            xAxis.labelRotationAngle = 45F
            description.isEnabled = false
//            setExtraOffsets(5F, 10F, 50F, 10F)
            data = wrapper.data
            animateY(400)
        }
    }
}

class DefaultChartHolder(private val binding: IDefaultChartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(entry: ChartDataWrapper<*>) {
    }
}

data class ChartDataWrapper<T : ChartData<*>>(val id: Int, val data: T)
