package com.artkachenko.ui_utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet

object AnimationUtils {

    private const val DEFAULT_ANIMATION_DURATION = 400L

    const val chartAnimationSteps = 15

    fun expandView(view: View, expand: Boolean) {
        if (!expand) {
            view.animate().translationY(view.height.toFloat())
                .setDuration(DEFAULT_ANIMATION_DURATION)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        view.visibility = View.GONE
                    }
                })
        } else if (view.visibility != View.VISIBLE) {
            view.animate()
                .translationY(0F)
                .setDuration(DEFAULT_ANIMATION_DURATION)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        view.visibility = View.VISIBLE
                    }
                })
        }
    }

    fun animateAlpha(view: View, shouldShow: Boolean, duration: Long = DEFAULT_ANIMATION_DURATION, delay: Long = 0) {
        if (!shouldShow) {
            view.animate().alpha(0F)
                .setStartDelay(delay)
                .setDuration(duration)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        view.visibility = View.GONE
                    }
                })
        } else if (view.visibility != View.VISIBLE || view.alpha == 0F) {
            view.animate()
                .alpha(1F)
                .setStartDelay(delay)
                .setDuration(duration)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        view.visibility = View.VISIBLE
                    }
                })
        }
    }

    fun rotateView(view: View, rotation: Float = 180F, clockWise: Boolean = true) {
        val direction = if (clockWise) 1 else -1
        view.animate().rotationBy(rotation * direction)
            .setDuration(DEFAULT_ANIMATION_DURATION)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
            }
        })
    }
}

class DetailTransition(duration: Long = 300, delay: Long = 0) : TransitionSet() {
    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
//            .addTransition(ChangeImageTransform())
            .setDuration(duration).setStartDelay(delay)
            .interpolator = AccelerateDecelerateInterpolator()
        excludeTarget(android.R.id.statusBarBackground, true)
        excludeTarget(android.R.id.navigationBarBackground, true)
    }
}