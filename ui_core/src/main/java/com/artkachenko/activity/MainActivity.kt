package com.artkachenko.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.artkachenko.ui_utils.AnimationUtils
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.themes.ThemeAwareInflater
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ImageUtils.CanHideBottomNavView {

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(
            LayoutInflater.from(this),
            ThemeAwareInflater(delegate)
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun showNavigationBar(show : Boolean) {
        val view = findViewById<BottomNavigationView>(R.id.nav_view)
        AnimationUtils.expandView(view, show)
    }
}