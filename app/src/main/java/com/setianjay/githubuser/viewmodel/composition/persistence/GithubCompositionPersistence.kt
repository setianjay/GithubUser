package com.setianjay.githubuser.viewmodel.composition.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.setianjay.githubuser.database.presistence.entity.User
import com.setianjay.githubuser.network.repository.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GithubCompositionPersistence private constructor(
    private val repository: GithubRepository,
    private val viewModelScope: CoroutineScope
) {
    companion object {
        fun getInstance(
            repository: GithubRepository,
            viewModelScope: CoroutineScope
        ): GithubCompositionPersistence {
            return GithubCompositionPersistence(repository, viewModelScope)
        }
    }

    /***** Add User Favorite *****/
    fun addUserFavorite(user: User) = viewModelScope.launch {
        repository.addUserFavorite(user)
    }

    /***** Add User Favorite *****/
    fun checkUserExists(username: String): LiveData<User?> =
        repository.getSpecificUser(username).asLiveData()

    /***** Delete User Favorite *****/
    fun deleteUserFavorite(user: User) = viewModelScope.launch {
        repository.deleteUserFavorite(user)
    }

    /***** Get User Favorite *****/
    fun getUserFavorite(): LiveData<List<User>> = repository.getUsers().asLiveData()
}