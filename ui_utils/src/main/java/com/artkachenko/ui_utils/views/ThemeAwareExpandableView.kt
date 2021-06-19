package com.artkachenko.ui_utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.artkachenko.ui_utils.AnimationUtils
import com.artkachenko.ui_utils.R
import com.artkachenko.ui_utils.themes.ViewScopeProvider
import com.artkachenko.ui_utils.themes.ViewScopeProviderImpl
import com.artkachenko.ui_utils.themes.ThemeManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeAwareExpandableView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle), ViewScopeProvider by ViewScopeProviderImpl() {

    private var isExpanded = false

    private var imageView: ImageView

    private var title: String = ""
    private var imageSize: Int = 24
    private var imageSrc = R.drawable.ic_arrow_down

    init {
        val array = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableView)

        kotlin.runCatching {
            title = array.getString(R.styleable.ExpandableView_title) ?: ""
            imageSrc = array.getInt(R.styleable.ExpandableView_image, R.drawable.ic_arrow_down)
        }

        array.recycle()

        val density = context.resources.displayMetrics.density

        val title = ThemeAwareTextView(context).apply {
            setTextAppearance(context, R.style.TextAppearance_AppCompat_Bold_20)
            text = title
        }

        imageView = ThemeAwareImageView(context).apply {
            val layoutParams =
                FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParams.height = (density * imageSize).toInt()
            layoutParams.width = (density * imageSize).toInt()
            layoutParams.gravity = Gravity.END
            this.layoutParams = layoutParams
            setImageResource(imageSrc)
        }

        addView(title)
        addView(imageView)

        setOnClickListener {
            val view = children.lastOrNull()
            view?.let {
                isExpanded = !isExpanded
                AnimationUtils.rotateView(imageView, clockWise = isExpanded)
                AnimationUtils.animateAlpha(view, isExpanded)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scope.launch {
            ThemeManager.themeFlow.collect {
                setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        it.viewGroupTheme.backgroundColor
                    )
                )
            }
        }
    }

    override fun onDetachedFromWindow() {
        parentJob.cancel()
        super.onDetachedFromWindow()
    }
}