package com.setianjay.githubuser.screens.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.setianjay.githubuser.databinding.ScreenSplashBinding
import com.setianjay.githubuser.screens.common.animations.Animations

class SplashScreenActivity : AppCompatActivity() {
    private val binding: ScreenSplashBinding by lazy { ScreenSplashBinding.inflate(layoutInflater) }
    private val animations: Animations by lazy { Animations(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAnimations()
        initListener()
    }

    private fun initAnimations(){
        binding.apply {
            // animation for image github
            ivGithub.startAnimation(animations.topToBottom())

            // animation for title
            tvTitle.startAnimation(animations.scaleToBig())

            // animation for subtitle
            tvSubtitle.startAnimation(animations.scaleToBig())

            // animation for button leave
            btnMove.startAnimation(animations.bottomToTop())
        }
    }
    
    private fun initListener(){
        binding.btnMove.setOnClickListener{
            startActivity(
                Intent(this@SplashScreenActivity, HomeActivity::class.java)
            )
            finish()
        }
    }
}