package com.artkachenko.calendar.calendar

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
            val sortedData = model.toList().sortedByDescending { it.second }.take(15)
            sourcesBarLegend.removeAllViews()
            val values = sortedData.map { it.second }

            sourcesBarChart.setData(values)
            populateLegend(sortedData, root.context)
        }
    }

    private fun populateLegend(keys: List<Pair<String, Double>>, context: Context) {
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
                val span = SpannableString("${s.first} ${s.second.toInt()} gr")
                val start = span.indexOfFirst { it.isDigit() }
                val end = span.length
                span.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, com.artkachenko.ui_utils.R.color.text_secondary)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                text = span
                setCompoundDrawables(img, null, null, null)
                compoundDrawablePadding = dp(8)
            }
            binding.sourcesBarLegend.addView(legendView)
        }
    }
}