package com.setianjay.githubuser.screens.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.setianjay.githubuser.R
import com.setianjay.githubuser.databinding.ActivityHomeBinding
import com.setianjay.githubuser.network.api.ApiService
import com.setianjay.githubuser.viewmodel.GithubViewModel
import com.setianjay.githubuser.viewmodel.GithubViewModelFactory

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private val viewModelFactory by lazy { GithubViewModelFactory(ApiService.githubApi) }

    private val viewModel by viewModels<GithubViewModel> { viewModelFactory }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
        setupObserver()
    }

    /* function to initialize all listener in this layout */
    private fun initListener(){
        // listener for show pop up menu
        binding.ivMenus.setOnClickListener {
            showPopupMenu()
        }
    }

    /* function for show of pop up menu*/
    private fun showPopupMenu(){
        val navController = findNavController(R.id.nav_host_fragment_container)

        val popupMenu = PopupMenu(this,binding.ivMenus)
        popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
            when(item?.itemId){
                R.id.favorites -> {
                    Toast.makeText(this@HomeActivity, "Under Development", Toast.LENGTH_SHORT).show()
                }
                R.id.settings -> {
                    navController.navigateUp()
                    navController.navigate(R.id.settingsFragment)
                }
            }
            true
        }

        popupMenu.show()
    }

    /* function to set up any observer in view model */
    private fun setupObserver(){
        // observe for the title and set with value if any update from current fragment
        viewModel.getTitle().observe(this@HomeActivity){ title ->
            binding.tvPage.text = title
        }
    }
}