package com.artkachenko.ui_utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

object AnimationUtils {

    const val DEFAULT_ANIMATION_DURATION = 400L

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

    fun animateAlpha(view: View, shouldShow: Boolean) {
        if (!shouldShow) {
            view.animate().alpha(0F)
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
                .alpha(1F)
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

    fun rotateView(view: View, rotation: Float = 180F, clockWise: Boolean = true) {
        var direction = if (clockWise) 1 else -1
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