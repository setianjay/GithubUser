package com.setianjay.githubuser.viewmodel.composition.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.setianjay.githubuser.model.user.UserDetailsModel
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.network.repository.GithubRepository
import com.setianjay.githubuser.network.resource.Resource
import com.setianjay.githubuser.utill.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class GithubCompositionNetwork private constructor(
    private val repository: GithubRepository,
    private val viewModelScope: CoroutineScope
) {
    private val users: MutableLiveData<Resource<List<UsersModel>>> = MutableLiveData()
    private val userDetails: MutableLiveData<Resource<UserDetailsModel>> = MutableLiveData()
    private val userFollowers: MutableLiveData<Resource<List<UsersModel>>> = MutableLiveData()
    private val userFollowing: MutableLiveData<Resource<List<UsersModel>>> = MutableLiveData()

    /***** Search *****/
    fun searchUsers(username: String) = viewModelScope.launch {
        users.value = Resource.loading()
        delay(500L)
        try {
            val response = repository.searchUsers(username)
            if (response.isSuccessful && response.body() != null) {
                val result = response.body()?.totalCount
                if (result != null) {
                    if (result > 0) {
                        users.value = Resource.success(response.body()?.users)
                    } else {
                        // ERROR USER NOT FOUND
                        users.value = Resource.error(Constant.ERROR.ERR_USERS_NOT_FOUND)
                    }
                }
            } else {
                // ERROR API
                users.value = Resource.error(Constant.ERROR.ERR_API)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            users.value = Resource.error(Constant.ERROR.ERR_API)
        }
    }

    fun getUsers(): LiveData<Resource<List<UsersModel>>> = users

    /***** Details *****/
    fun userDetails(username: String) = viewModelScope.launch {
        userDetails.value = Resource.loading()
        delay(500L)
        try {
            val response = repository.getDetails(username)
            if (response.isSuccessful && response.body() != null) {
                userDetails.value = Resource.success(response.body())
            } else {
                userDetails.value = Resource.error(Constant.ERROR.ERR_API)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            userDetails.value = Resource.error(Constant.ERROR.ERR_API)
        }
    }

    fun getUserDetails(): LiveData<Resource<UserDetailsModel>> = userDetails

    /***** Followers *****/
    fun userFollowers(username: String) = viewModelScope.launch {
        userFollowers.value = Resource.loading()
        delay(10L)
        try {
            val response = repository.getFollowers(username)
            if (response.isSuccessful && response.body() != null) {
                val result = response.body()
                if (result?.isNotEmpty() == true) {
                    userFollowers.value = Resource.success(response.body())
                } else {
                    userFollowers.value = Resource.error(Constant.ERROR.ERR_USERS_NOT_FOUND)
                }
            } else {
                userFollowers.value = Resource.error(Constant.ERROR.ERR_API)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            userFollowers.value = Resource.error(Constant.ERROR.ERR_API)
        }
    }

    fun getFollowers(): LiveData<Resource<List<UsersModel>>> = userFollowers

    /***** Following *****/
    fun userFollowing(username: String) = viewModelScope.launch {
        userFollowing.value = Resource.loading()
        try {
            val response = repository.getFollowing(username)
            if (response.isSuccessful && response.body() != null) {
                val result = response.body()
                if (result?.isNotEmpty() == true) {
                    userFollowing.value = Resource.success(response.body())
                } else {
                    userFollowing.value = Resource.error(Constant.ERROR.ERR_USERS_NOT_FOUND)
                }
            } else {
                userFollowing.value = Resource.error(Constant.ERROR.ERR_API)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            userFollowing.value = Resource.error(Constant.ERROR.ERR_API)
        }
    }

    fun getFollowing(): LiveData<Resource<List<UsersModel>>> = userFollowing

    companion object {
        fun getInstance(
            repository: GithubRepository,
            viewModelScope: CoroutineScope
        ): GithubCompositionNetwork {
            return GithubCompositionNetwork(repository, viewModelScope)
        }
    }
}