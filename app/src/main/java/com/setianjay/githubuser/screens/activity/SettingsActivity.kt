package com.setianjay.githubuser.screens.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.setianjay.githubuser.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
    }

    /* function to initialize all listener in this layout */
    private fun initListener(){
        // listener for button back
        binding.ivBack.setOnClickListener {
            onBackPressed()
            finish()
        }

        // listener for change language
        binding.containerLanguage.setOnClickListener {
            Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS).also {
                startActivity(it)
            }
        }
    }
}