package com.artkachenko.settings

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.artkachenko.settings.databinding.FragmentSettingsBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.themes.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.hypot

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        binding.darkThemeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            val newTheme = when (ThemeManager.theme) {
                ThemeManager.Theme.DARK -> ThemeManager.Theme.LIGHT
                ThemeManager.Theme.LIGHT -> ThemeManager.Theme.DARK
            }
            setTheme(newTheme)
        }
    }

    override fun onResume() {
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(true)
        super.onResume()
    }

    private fun setTheme(theme: ThemeManager.Theme) {
        with(binding) {
            if (themeImageView.isVisible) {
                return
            }

            val w = container.measuredWidth
            val h = container.measuredHeight

            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            container.draw(canvas)

            themeImageView.setImageBitmap(bitmap)
            themeImageView.isVisible = true

            val finalRadius = hypot(w.toFloat(), h.toFloat())

            ThemeManager.theme = theme

            val anim =
                ViewAnimationUtils.createCircularReveal(container, w / 2, h / 2, 0f, finalRadius)
            anim.duration = 400L
            anim.doOnEnd {
                themeImageView.setImageDrawable(null)
                themeImageView.isVisible = false
            }
            anim.start()
        }
    }
}
