package com.setianjay.githubuser.network.repository

import com.setianjay.githubuser.database.preference.SettingsPreference
import com.setianjay.githubuser.database.presistence.AppDatabase
import com.setianjay.githubuser.database.presistence.entity.User
import com.setianjay.githubuser.network.api.GithubEndPoint
import kotlinx.coroutines.flow.Flow

class GithubRepository(
    private val githubApi: GithubEndPoint,
    private val db: AppDatabase,
    private val pref: SettingsPreference
) {
    /************************** API **************************/
    suspend fun getDetails(username: String) = githubApi.getDetails(username)

    suspend fun getFollowers(username: String) = githubApi.getFollowers(username)

    suspend fun getFollowing(username: String) = githubApi.getFollowing(username)

    suspend fun searchUsers(username: String) = githubApi.searchUsers(username)

    /************************** PERSISTENCE **************************/
    suspend fun addUserFavorite(user: User) = db.userDao().addFavorite(user)

    suspend fun deleteUserFavorite(user: User) = db.userDao().deleteFavorite(user)

    fun getUsers(): Flow<List<User>> = db.userDao().getUsers()

    fun getSpecificUser(username: String): Flow<User?> = db.userDao().getSpecificUser(username)

    /************************** PREFERENCES **************************/
    suspend fun setTheme(isDarkModeActive: Boolean) = pref.saveThemeSetting(isDarkModeActive)

    fun getTheme(): Flow<Boolean> = pref.getThemeSetting()
}