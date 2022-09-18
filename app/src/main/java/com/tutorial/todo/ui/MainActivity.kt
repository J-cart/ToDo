package com.tutorial.todo.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tutorial.todo.R
import com.tutorial.todo.database.AppDataBase
import com.tutorial.todo.databinding.ActivityMainBinding
import com.tutorial.todo.model.arch.ActivityViewModel

class MainActivity : AppCompatActivity() {
    val activityViewModel: ActivityViewModel by viewModels()
    private lateinit var navContoller: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        activityViewModel.init(AppDataBase.getDatabase(this))

        val fragHost = supportFragmentManager.findFragmentById(R.id.fragHost) as NavHostFragment
        navContoller = fragHost.findNavController()

        appBarConfiguration = AppBarConfiguration(setOf(R.id.listFragment, R.id.profileFragment))
        setupActionBarWithNavController(navContoller, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navContoller)

        navContoller.addOnDestinationChangedListener { controller, destination, arguments ->
            if (appBarConfiguration.topLevelDestinations.contains(destination.id)) {
                binding.bottomNav.isVisible = true
            } else {
                binding.bottomNav.isGone = true
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navContoller.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun showKeyboard() {
        val imm = application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideKeyboard(view: View) {
        val imm = application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}