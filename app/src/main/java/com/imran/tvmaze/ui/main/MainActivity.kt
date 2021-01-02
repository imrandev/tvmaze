package com.imran.tvmaze.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.imran.tvmaze.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this, getNavController(), getAppBarConfig())
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(getNavController(), getAppBarConfig())
    }

    private fun getNavHostFragment() = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

    private fun getNavController() = getNavHostFragment().navController

    private fun getAppBarConfig() = AppBarConfiguration.Builder(getNavController().graph).build()
}