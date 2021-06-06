package com.artkachenko.settings

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.artkachenko.core_api.utils.PrefManager
import com.artkachenko.settings.databinding.FragmentSettingsBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.themes.Theme
import com.artkachenko.ui_utils.themes.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.hypot

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var prefManager: PrefManager

    @Inject
    lateinit var themeManager: ThemeManager

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        binding.darkThemeSwitch.isChecked = prefManager.isDarkTheme

        binding.darkThemeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            val newTheme = when (themeManager.theme) {
                Theme.DARK -> Theme.LIGHT
                Theme.LIGHT -> Theme.DARK
            }
            prefManager.isDarkTheme = newTheme == Theme.DARK
            setTheme(newTheme)
        }
    }

    override fun onResume() {
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(true)
        super.onResume()
    }

    private fun setTheme(theme: Theme) {
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

            val animX = darkThemeSwitch.x.toInt() + darkThemeSwitch.width / 2
            val animY = darkThemeSwitch.y.toInt() + darkThemeSwitch.height / 2

            val finalRadius = hypot(w.toFloat(), h.toFloat())

            themeManager.theme = theme

            val anim =
                ViewAnimationUtils.createCircularReveal(container, animX, animY, 0f, finalRadius)
            anim.duration = 400L
            anim.doOnEnd {
                themeImageView.setImageDrawable(null)
                themeImageView.isVisible = false
            }
            anim.start()
        }
    }
}
