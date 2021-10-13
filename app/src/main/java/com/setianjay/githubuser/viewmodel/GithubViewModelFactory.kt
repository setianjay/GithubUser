package com.setianjay.githubuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.setianjay.githubuser.database.preference.SettingsPreference
import com.setianjay.githubuser.database.presistence.AppDatabase
import com.setianjay.githubuser.network.api.GithubEndPoint
import com.setianjay.githubuser.network.repository.GithubRepository
import java.lang.IllegalArgumentException

class GithubViewModelFactory private constructor(
    private val api: GithubEndPoint,
    private val db: AppDatabase,
    private val pref: SettingsPreference
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GithubViewModel::class.java)) {
            return GithubViewModel(GithubRepository(api, db, pref)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

    companion object {
        @Volatile
        private var INSTANCE: GithubViewModelFactory? = null

        fun getInstance(
            api: GithubEndPoint,
            db: AppDatabase,
            pref: SettingsPreference
        ): GithubViewModelFactory {
            return INSTANCE ?: synchronized(this){
                val instance = GithubViewModelFactory(api, db, pref)
                INSTANCE = instance
                instance
            }
        }
    }
}