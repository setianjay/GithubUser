package com.setianjay.githubuser.screens.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import com.setianjay.githubuser.R
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
            showPopupMenu()
        }
    }

    private fun showPopupMenu(){
        val popupMenu = PopupMenu(this,binding.ivMenus)
        popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
            when(item!!.itemId){
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
}