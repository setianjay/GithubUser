package com.setianjay.githubuser.screens.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.setianjay.githubuser.database.preference.SettingsPreference
import com.setianjay.githubuser.database.presistence.DatabaseBuilder
import com.setianjay.githubuser.databinding.ActivitySplashScreenBinding
import com.setianjay.githubuser.network.api.ApiService
import com.setianjay.githubuser.screens.common.animations.Animations
import com.setianjay.githubuser.utill.dataStore
import com.setianjay.githubuser.viewmodel.GithubViewModel
import com.setianjay.githubuser.viewmodel.GithubViewModelFactory

class SplashScreenActivity : AppCompatActivity() {
    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(
            layoutInflater
        )
    }
    private val anim: Animations by lazy { Animations(this) }

    private val viewModelFactory by lazy {
        GithubViewModelFactory.getInstance(
            ApiService.githubApi,
            DatabaseBuilder.getInstance(this.applicationContext),
            SettingsPreference.getInstance(this.applicationContext.dataStore)
        )
    }

    private val viewModel by viewModels<GithubViewModel>{
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAnimations()
        initListener()
        setupObserver()
    }

    private fun initAnimations() {
        binding.apply {
            // animation for image github
            ivGithub.startAnimation(anim.topToBottom())

            // animation for title
            tvTitle.startAnimation(anim.scaleToBig())

            // animation for subtitle
            tvSubtitle.startAnimation(anim.scaleToBig())

            // animation for button leave
            btnMove.startAnimation(anim.bottomToTop())
        }
    }

    /* function to initialize all listener in this layout */
    private fun initListener() {
        binding.btnMove.setOnClickListener {
            startActivity(
                Intent(this@SplashScreenActivity, HomeActivity::class.java)
            )
            finish()
        }
    }

    /* function to set up any observer in view model */
    private fun setupObserver() {
        // observe theme of the application, is it dark mode or light mode
        viewModel.app.getTheme().observe(this@SplashScreenActivity) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}