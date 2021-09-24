package com.setianjay.githubuser.network.repository

import com.setianjay.githubuser.database.presistence.AppDatabase
import com.setianjay.githubuser.database.presistence.entity.User
import com.setianjay.githubuser.network.api.GithubEndPoint
import kotlinx.coroutines.flow.Flow

class GithubRepository(
    private val githubApi: GithubEndPoint,
    private val db: AppDatabase
) {
    /************************** API **************************/
    suspend fun getDetails(username: String) = githubApi.getDetails(username)

    suspend fun getFollowers(username: String) = githubApi.getFollowers(username)

    suspend fun getFollowing(username: String) = githubApi.getFollowing(username)

    suspend fun searchUsers(username: String) = githubApi.searchUsers(username)

    /************************** DATABASE **************************/
    suspend fun addUserFavorite(user: User) = db.userDao().addFavorite(user)

    suspend fun deleteUserFavorite(user: User) = db.userDao().deleteFavorite(user)

    fun getSpecificUser(username: String): Flow<User?> = db.userDao().getSpecificUser(username)
}