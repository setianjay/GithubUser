package com.setianjay.githubuser.network.api

import com.setianjay.githubuser.model.user.UserDetailsModel
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.network.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubEndPoint {

    @GET("users/{username}")
    suspend fun getDetails(@Path("username") username: String): Response<UserDetailsModel>

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): Response<List<UsersModel>>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): Response<List<UsersModel>>

    @GET("users")
    suspend fun getUsers(): Response<List<UsersModel>>

    @GET("search/users")
    suspend fun searchUsers(@Query("q") username: String): Response<SearchResponse>
}