package com.artkachenko.settings

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.artkachenko.core_api.utils.PrefManager
import com.artkachenko.settings.databinding.FragmentSettingsBinding
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.themes.Theme
import com.artkachenko.ui_utils.themes.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import viewBinding
import javax.inject.Inject
import kotlin.math.hypot


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var prefManager: PrefManager

    @Inject
    lateinit var themeManager: ThemeManager

    private var binding by viewBinding<FragmentSettingsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        with(binding) {
            darkThemeSwitch.isChecked = prefManager.isDarkTheme

            darkThemeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                val newTheme = when (themeManager.theme) {
                    Theme.DARK -> Theme.LIGHT
                    Theme.LIGHT -> Theme.DARK
                }
                prefManager.isDarkTheme = newTheme == Theme.DARK
                setTheme(newTheme)
            }

            desiredCaloriesText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    val parsedNumber = s.toString().toIntOrNull()
                    parsedNumber?.let { prefManager.desiredCalories = it }
                }
            })

            if (desiredCaloriesText.text.isNullOrEmpty()) desiredCaloriesText.setText(prefManager.desiredCalories.toString())

            desiredCaloriesText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    desiredCaloriesText.clearFocus()
                }
                false
            }
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
