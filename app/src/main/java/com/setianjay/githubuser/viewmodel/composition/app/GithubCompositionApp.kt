package com.setianjay.githubuser.viewmodel.composition.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.setianjay.githubuser.network.repository.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GithubCompositionApp private constructor(
    private val repository: GithubRepository,
    private val viewModelScope: CoroutineScope
) {
    private val title: MutableLiveData<String> = MutableLiveData()

    companion object{
        fun getInstance(
            repository: GithubRepository,
            viewModelScope: CoroutineScope
        ): GithubCompositionApp {
            return GithubCompositionApp(repository, viewModelScope)
        }
    }

    /***** Title *****/
    fun setTitle(title: String) {
        this.title.postValue(title)
    }

    fun getTitle(): LiveData<String> = title

    /***** Theme *****/
    fun setTheme(isDarkModeActive: Boolean) = viewModelScope.launch {
        repository.setTheme(isDarkModeActive)
    }

    fun getTheme(): LiveData<Boolean> = repository.getTheme().asLiveData()
}