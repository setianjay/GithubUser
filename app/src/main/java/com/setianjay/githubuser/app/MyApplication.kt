package com.setianjay.githubuser.app

import android.app.Application
import com.setianjay.githubuser.BuildConfig
import com.setianjay.githubuser.database.preference.SettingsPreference
import com.setianjay.githubuser.database.presistence.DatabaseBuilder
import com.setianjay.githubuser.network.api.ApiService
import com.setianjay.githubuser.utill.dataStore
import com.setianjay.githubuser.viewmodel.GithubViewModelFactory
import timber.log.Timber

class MyApplication: Application() {
    val viewModelFactory by lazy {
        GithubViewModelFactory(
            ApiService.githubApi,
            DatabaseBuilder.getInstance(applicationContext),
            SettingsPreference.getInstance(applicationContext.dataStore)
        )
    }

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