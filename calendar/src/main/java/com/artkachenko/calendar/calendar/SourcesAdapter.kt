package com.artkachenko.calendar.calendar

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateMargins
import com.artkachenko.calendar.R
import com.artkachenko.calendar.databinding.IBarItemBinding
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.ui_utils.dp
import com.artkachenko.ui_utils.inflater
import com.artkachenko.ui_utils.themes.Themes
import com.artkachenko.ui_utils.views.ThemeAwareTextView

class SourcesAdapter : BaseAdapter<Map<String, Double>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Map<String, Double>> {
        val binding = IBarItemBinding.inflate(parent.inflater(), parent, false)
        return SourcesViewHolder(binding)
    }
}

class SourcesViewHolder(private val binding: IBarItemBinding) :
    BaseViewHolder<Map<String, Double>>(binding.root) {
    override fun bind(model: Map<String, Double>) {
        with(binding) {
            sourcesBarLegend.removeAllViews()
            val values = model.values.toList()
            val keys = model.keys.toList()
            sourcesBarChart.setData(values)
            populateLegend(keys, root.context)
        }
    }

    private fun populateLegend(keys: List<String>, context: Context) {
        keys.forEachIndexed { index, s ->
            val legendView = ThemeAwareTextView(context).apply {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.updateMargins(bottom = context.dp(8F))
                this.layoutParams = params
                setTextAppearance(context, R.style.TextAppearance_AppCompat_Medium_16)
                val img = ContextCompat.getDrawable(context, R.drawable.bg_circle)
                val tintColor = ContextCompat.getColor(context, Themes.chartColors[index])
                img?.setBounds(0,0,24,24)
                img?.setTint(tintColor)
                text = s
                this.setCompoundDrawables(img, null, null, null)
            }
            binding.sourcesBarLegend.addView(legendView)
        }
    }
}