package com.setianjay.githubuser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.network.repository.GithubRepository
import com.setianjay.githubuser.network.resource.Resource
import com.setianjay.githubuser.utill.Constant
import kotlinx.coroutines.launch
import java.lang.Exception

class GithubViewModel(private val repository: GithubRepository): ViewModel() {
    private val title: MutableLiveData<String> = MutableLiveData()
    private val users: MutableLiveData<Resource<List<UsersModel>>> = MutableLiveData()


    fun setTitle(title: String){
        this.title.postValue(title)
    }

    fun getTitle(): MutableLiveData<String> = title

    fun searchUsers(username: String) = viewModelScope.launch {
        users.value = Resource.loading()
        try {
            val response = repository.searchUsers(username)
            if (response.isSuccessful && response.body() != null){
                val result = response.body()?.totalCount
                if (result != null){
                    if (result > 0){
                        users.value = Resource.success(response.body()?.users)
                    }else{
                        // ERROR USER NOT FOUND
                        users.value = Resource.error(Constant.ERROR.ERR_USERS_NOT_FOUND)
                    }
                }
            }else{
                // ERROR API
                users.value = Resource.error(Constant.ERROR.ERR_API)
            }
        }catch (e: Exception){
            e.printStackTrace()
            users.value = Resource.error(Constant.ERROR.ERR_API)
        }
    }

    fun getUsers(): MutableLiveData<Resource<List<UsersModel>>> = users
}