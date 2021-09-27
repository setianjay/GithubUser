package com.setianjay.githubuser.utill

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_pref")

/* extension function for hide the keyboard for any context */
fun Context.hideKeyboard(view: View) {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/* extension function for load image from url */
fun ImageView.load(url: String){
    Glide.with(this.context)
        .load(url)
        .into(this)
}

/* extension function for set resource image / icon on floating action button */
fun FloatingActionButton.src(res: Resources, drawable: Int){
    this.setImageDrawable(ResourcesCompat.getDrawable(res, drawable, null))
}