package com.setianjay.githubuser.screens.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.setianjay.githubuser.R
import com.setianjay.githubuser.databinding.ActivityHomeBinding
import com.setianjay.githubuser.network.api.ApiService
import com.setianjay.githubuser.viewmodel.GithubViewModel
import com.setianjay.githubuser.viewmodel.GithubViewModelFactory

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private lateinit var viewModel: GithubViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewModel()
        initListener()
        setupObserver()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(
            this@HomeActivity,
            GithubViewModelFactory(ApiService.githubApi)
        ).get(GithubViewModel::class.java)
    }
    
    private fun initListener(){
        binding.ivMenus.setOnClickListener {
            showPopupMenu()
        }
    }

    private fun showPopupMenu(){
        val popupMenu = PopupMenu(this,binding.ivMenus)
        popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
            when(item?.itemId){
                R.id.favorites -> {
                    Toast.makeText(this@HomeActivity, "Under Development", Toast.LENGTH_SHORT).show()
                }
                R.id.settings -> {
                    Toast.makeText(this@HomeActivity, "Under Development", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        popupMenu.show()
    }

    private fun setupObserver(){
        // observe for the title and set with value if any update from current fragment
        viewModel.getTitle().observe(this@HomeActivity){ title ->
            binding.tvPage.text = title
        }
    }
}