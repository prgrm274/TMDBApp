package com.programmer2704.movapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.programmer2704.movapp.databinding.ActivityMainBinding
import com.programmer2704.movapp.forfragment.DefaultFragmentFactory
import com.programmer2704.movapp.forfragment.DefaultFragmentFactoryEntryPoint
import com.programmer2704.movapp.view.viewmodel.PopularViewmodel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val b: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var fragmentFactory: DefaultFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromActivity(
            this,
            DefaultFragmentFactoryEntryPoint::class.java)
        supportFragmentManager.fragmentFactory = entryPoint.getFragmentFactory()



        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.includedAppbar.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_upcoming,
                R.id.nav_popular,
                R.id.nav_favorites
            ),
            b.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        b.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}