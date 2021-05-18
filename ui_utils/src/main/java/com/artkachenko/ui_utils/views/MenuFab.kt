package com.artkachenko.ui_utils.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Point
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.updateMargins
import com.artkachenko.ui_utils.R
import com.artkachenko.ui_utils.dp
import com.artkachenko.ui_utils.dpF
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuFab @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyleRes: Int = 0
) : FrameLayout(context, attributeSet, defStyleRes) {
    private var menuOpened = false

    private val animationDuration = 200L

    private val fabs = mutableListOf<FloatingActionButton>()

    private val mainFab by lazy {
        FloatingActionButton(context).apply {
            elevation = 8F
        }
    }

    init {

        setBackgroundColor(Color.TRANSPARENT)

        val mainFabConfig = FabConfig(
            icon = R.drawable.ic_baseline_add_24,
            margins = 16F,
            visibility = View.VISIBLE
        ) {
            it as FloatingActionButton
            if (!menuOpened) {
                fabs.first().performClick()
            } else {
                menuOpened = !menuOpened
                showMenu(it, menuOpened)
            }
        }

        setupFab(mainFab, mainFabConfig)

        mainFab.setOnClickListener {
            it as FloatingActionButton
            menuOpened = !menuOpened
            showMenu(it, menuOpened)
        }
    }

    fun addFab(config: FabConfig) {
        val fab = FloatingActionButton(context)

        setupFab(fab, config)

        fabs.add(fab)
    }

    private fun setupFab(fab: FloatingActionButton, config: FabConfig) {
        addView(fab)

        fab.visibility = config.visibility

        val params = fab.layoutParams as FrameLayout.LayoutParams
        params.height = dp(config.size.y)
        params.width = dp(config.size.x)
        params.gravity = Gravity.END or Gravity.BOTTOM
        val margin = dp(config.margins)
        params.updateMargins(margin, margin, margin, margin)
        fab.layoutParams = params
        fab.setImageDrawable(config.icon?.let { ContextCompat.getDrawable(context, it) })
        config.background?.let { fab.background = context.resources.getDrawable(it, null) }
        fab.scaleType = ImageView.ScaleType.CENTER
        config.backgroundTint?.let { fab.backgroundTintList = ColorStateList.valueOf(it) }
        fab.setOnClickListener {
            config.listener.invoke(it)
        }
    }

    private fun showMenu(fab: FloatingActionButton, open: Boolean) {
        if (menuOpened) {
            isClickable = true
            isFocusable = true
            setOnClickListener {
                menuOpened = false
                showMenu(mainFab, menuOpened)
            }
        } else {
            isClickable = false
            isFocusable = false
            setOnClickListener(null)
        }

        val startColor = if (open) Color.TRANSPARENT else Color.parseColor("#80000000")
        val endColor = if (open) Color.parseColor("#80000000") else Color.TRANSPARENT

        val colorAnimation: ValueAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor).apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = animationDuration
                addUpdateListener {
                    setBackgroundColor(it.animatedValue as Int)
                }
            }

        val fabRotation = if (open) 45F else 90F

        colorAnimation.start()
        fab.animate().apply {
            duration = 200L
            interpolator = AccelerateDecelerateInterpolator()
            rotation(fabRotation)
        }

        if (open) {
            fabs.forEachIndexed { index, floatingActionButton ->
                animateSlideIn(
                    floatingActionButton,
                    index + 1
                )
            }
        } else {
            fabs.forEachIndexed { index, floatingActionButton ->
                animateSlideOut(
                    floatingActionButton
                )
            }
        }
    }

    private fun animateSlideIn(view: View, yOffset: Int = 0) {
        view.alpha = 0F

        view.animate()
//            .translationX(dpF(-100F + 50 * yOffset ))
            .translationY(dpF(-80F * yOffset))
            .alpha(1.0F)
            .setDuration(animationDuration)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.VISIBLE
                    view.alpha = 0F
                }
            })
    }

    private fun animateSlideOut(view: View, yOffset: Int = 0) {
        view.animate()
//            .translationX(dpF(- 50 * yOffset ))
            .translationY(dpF(80F * yOffset))
            .alpha(0.0F)
            .setDuration(animationDuration)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.GONE
                }
            })
    }

    data class FabConfig(
        val size: Point = Point(56, 56),
        @DrawableRes val icon: Int? = null,
        @DrawableRes val background: Int? = R.drawable.fab_bg,
        @ColorInt val backgroundTint: Int? = null,
        val margins: Float = 0F,
        val visibility: Int = View.GONE,
        val listener: (View) -> Unit
    )
}