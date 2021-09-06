package com.setianjay.githubuser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.setianjay.githubuser.network.repository.GithubRepository

class GithubViewModel(private val repository: GithubRepository): ViewModel() {
    private val title: MutableLiveData<String> = MutableLiveData()


    fun setTitle(title: String){
        this.title.postValue(title)
    }

    fun getTitle(): MutableLiveData<String> = title
}