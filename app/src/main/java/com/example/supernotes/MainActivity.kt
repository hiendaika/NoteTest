package com.example.supernotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.supernotes.databinding.ActivityMainBinding
import com.example.supernotes.utils.viewModelFactory
import com.example.supernotes.view.viewmodel.DashBoardViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: DashBoardViewModel by viewModels {
        viewModelFactory { DashBoardViewModel(this.application) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel

        initViews(binding)
        observerThemeMode()
        observerNavEvent(binding,navHostFragment.navController)
    }

    private fun observerThemeMode() {

    }

    private fun observerNavEvent(binding: ActivityMainBinding, navController: NavController) {
        navController.addOnDestinationChangedListener{_,destination,_ ->
            when(destination.id){
                R.id.fm_dashboard -> {
                    //Neu la man hinh dashboard thi k show man title tren toolbar
                    supportActionBar?.setDisplayShowTitleEnabled(false)
                }
                R.id.fm_add_note -> {
                    supportActionBar?.setDisplayShowTitleEnabled(true)
                    binding.toolbar.title = getString(R.string.add_note)
                }
                R.id.fm_detail -> {
                    supportActionBar?.setDisplayShowTitleEnabled(true)
                    binding.toolbar.title = getString(R.string.detail)
                }
            }

        }
    }

    private fun initViews(binding: ActivityMainBinding) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return

        with(navHostFragment.navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onSupportNavigateUp()
    }


}