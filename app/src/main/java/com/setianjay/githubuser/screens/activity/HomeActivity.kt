package com.setianjay.githubuser.screens.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.setianjay.githubuser.R
import com.setianjay.githubuser.app.MyApplication
import com.setianjay.githubuser.databinding.ActivityHomeBinding
import com.setianjay.githubuser.viewmodel.GithubViewModel

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<GithubViewModel> { (application as MyApplication).viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
        setupObserver()
    }

    /* function to initialize all listener in this layout */
    private fun initListener() {
        // listener for show pop up menu
        binding.ivMenus.setOnClickListener {
            showPopupMenu()
        }
    }

    /* function for show of pop up menu*/
    private fun showPopupMenu() {
        val navController = findNavController(R.id.nav_host_fragment_container)

        val popupMenu = PopupMenu(this, binding.ivMenus)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.favorites -> {
                    navController.navigateUp()
                    navController.navigate(R.id.favoriteFragment)
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
    private fun setupObserver() {
        // observe for the title and set with value if any update from current fragment
        viewModel.app.getTitle().observe(this@HomeActivity) { title ->
            binding.tvPage.text = title
        }
    }
}