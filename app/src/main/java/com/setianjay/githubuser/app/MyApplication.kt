package com.setianjay.githubuser.app

import android.app.Application
import com.setianjay.githubuser.BuildConfig
import timber.log.Timber

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber(){
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}