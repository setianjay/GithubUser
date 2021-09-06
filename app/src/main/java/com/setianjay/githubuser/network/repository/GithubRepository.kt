package com.setianjay.githubuser.network.repository

import com.setianjay.githubuser.network.api.GithubEndPoint

class GithubRepository(private val githubApi: GithubEndPoint) {

    suspend fun getDetails(username: String) = githubApi.getDetails(username)

    suspend fun getFollowers(username: String) = githubApi.getFollowers(username)

    suspend fun getFollowing(username: String) = githubApi.getFollowers(username)

    suspend fun searchUsers(username: String) = githubApi.searchUsers(username)
}