package com.setianjay.githubuser.screens.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.setianjay.githubuser.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
    }
    
    private fun initListener(){
        binding.ivMenus.setOnClickListener {
            Toast.makeText(this@HomeActivity, "Under Development", Toast.LENGTH_SHORT).show()
        }
    }
}