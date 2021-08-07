package com.setianjay.githubuser.screens.common.animations

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.setianjay.githubuser.R

class Animations(private val context: Context) {

    fun scaleToBig(): Animation =
        AnimationUtils.loadAnimation(context, R.anim.scale_to_big)

    fun topToBottom(): Animation =
        AnimationUtils.loadAnimation(context, R.anim.top_to_bottom)

    fun bottomToTop(): Animation =
        AnimationUtils.loadAnimation(context, R.anim.bottom_to_top)
}