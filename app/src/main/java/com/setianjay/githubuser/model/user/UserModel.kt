package com.setianjay.githubuser.model.user

data class UserModel(
    val username: String,
    val name: String,
    val location: String,
    val repository: Int,
    val company: String,
    val followers: Int,
    val following: Int,
    val avatar: Int
)