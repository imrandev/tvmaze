package com.imran.tvmaze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.imran.tvmaze.R
import com.imran.tvmaze.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //NavigationUI.setupActionBarWithNavController(this, getNavController(), getAppBarConfig())
        setUpBottomNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(getNavController(), getAppBarConfig())
    }

    private fun getNavHostFragment() = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

    private fun getNavController() = getNavHostFragment().navController

    private fun getAppBarConfig() = AppBarConfiguration.Builder(getNavController().graph).build()

    private fun setUpBottomNavigation() {
        activityMainBinding.bottomNavigation.setupWithNavController(getNavController())
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.dashboardFragment,
                R.id.browseFragment
            )
        )
        NavigationUI.setupActionBarWithNavController(this, getNavController(), getAppBarConfig())
    }

    override fun onNavigateUp(): Boolean {
        return getNavController().navigateUp() || super.onNavigateUp()
    }
}