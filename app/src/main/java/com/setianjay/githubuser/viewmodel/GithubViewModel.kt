package com.setianjay.githubuser.viewmodel

import androidx.lifecycle.*
import com.setianjay.githubuser.network.repository.GithubRepository
import com.setianjay.githubuser.viewmodel.composition.app.GithubCompositionApp
import com.setianjay.githubuser.viewmodel.composition.network.GithubCompositionNetwork
import com.setianjay.githubuser.viewmodel.composition.persistence.GithubCompositionPersistence

class GithubViewModel(repository: GithubRepository) : ViewModel() {

    /* for logic related to application display */
    val app = GithubCompositionApp.getInstance(repository, viewModelScope)

    /* for logic related to networking and API */
    val network = GithubCompositionNetwork.getInstance(repository, viewModelScope)

    /* for logic related to local persistence */
    val db = GithubCompositionPersistence.getInstance(repository, viewModelScope)
}