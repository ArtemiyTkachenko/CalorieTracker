package com.artkachenko.calendar.calendar

import android.view.LayoutInflater
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.calendar.databinding.IBarChartBinding
import com.artkachenko.calendar.databinding.IDefaultChartBinding
import com.artkachenko.calendar.databinding.IPieChartBinding
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.ui_utils.dp
import com.github.mikephil.charting.data.*

class ChartAdapter : ListAdapter<ChartDataWrapper<*>, RecyclerView.ViewHolder>(object :
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
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            PIE_ENTRY -> {
                val binding = IPieChartBinding.inflate(inflater, parent, false)
                PieChartHolder(binding)
            }
            BAR_ENTRY -> {
                val binding = IBarChartBinding.inflate(inflater, parent, false)
                BarChartHolder(binding)
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

class BarChartHolder(private val binding: IBarChartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(wrapper: ChartDataWrapper<BarData>) {
        with(binding.barChart) {
            val labels = binding.labels
            labels.removeAllViews()
            val stackLabels = wrapper.data.dataSets.first().stackLabels
            labels.weightSum = stackLabels?.size?.toFloat() ?: 0F
            stackLabels.forEachIndexed { index, s ->
                val label = TextView(itemView.context).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                            .apply {
                                val firstItemOffset =
                                    if (index == 0 && stackLabels.size > 1) dp(10F) else 0
                                val lastItemOffset =
                                    if (index == stackLabels.size - 1 && stackLabels.size > 1) dp(
                                        10F
                                    ) else 0
                                val margin = dp(8F)
                                updateMargins(
                                    left = margin + firstItemOffset,
                                    top = margin,
                                    bottom = margin,
                                    right = margin + lastItemOffset
                                )
                                textAlignment = TEXT_ALIGNMENT_CENTER
                                textSize = 14F
                            }
                    text = s
                }
                labels.addView(label)
            }
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
