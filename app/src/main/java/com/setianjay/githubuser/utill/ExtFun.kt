package com.setianjay.githubuser.utill

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide


fun Context.hideKeyboard(view: View) {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun ImageView.load(url: String){
    Glide.with(this.context)
        .load(url)
        .into(this)
}
