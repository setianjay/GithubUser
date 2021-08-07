package com.setianjay.githubuser.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.setianjay.githubuser.databinding.ScreenSplashBinding
import com.setianjay.githubuser.screens.common.animations.Animations

class SplashScreen : AppCompatActivity() {
    private val binding: ScreenSplashBinding by lazy { ScreenSplashBinding.inflate(layoutInflater) }
    private val animations: Animations by lazy { Animations(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAnimations()
        initListener()
    }

    private fun initAnimations(){
        // animation for image github
        binding.ivGithub.startAnimation(animations.topToBottom())

        // animation for title
        binding.tvTitle.startAnimation(animations.scaleToBig())

        // animation for subtitle
        binding.tvSubtitle.startAnimation(animations.scaleToBig())

        // animation for button leave
        binding.btnMove.startAnimation(animations.bottomToTop())
    }
    
    private fun initListener(){
        binding.btnMove.setOnClickListener{
            Toast.makeText(this@SplashScreen, "Move to HomeActivity", Toast.LENGTH_SHORT).show()
        }
    }
}