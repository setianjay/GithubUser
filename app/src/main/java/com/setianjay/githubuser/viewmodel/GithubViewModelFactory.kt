package com.setianjay.githubuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.setianjay.githubuser.network.api.GithubEndPoint
import com.setianjay.githubuser.network.repository.GithubRepository
import java.lang.IllegalArgumentException

class GithubViewModelFactory(private val api: GithubEndPoint): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GithubViewModel::class.java)){
            return GithubViewModel(GithubRepository(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}