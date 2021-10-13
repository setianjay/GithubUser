package com.setianjay.githubuser.model.user

import com.google.gson.annotations.SerializedName

data class UserDetailsModel(
    @SerializedName("avatar_url") val avatar: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("name") val name: String,
    @SerializedName("login") val username: String,
    @SerializedName("followers") val totalFollowers: Int,
    @SerializedName("following") val totalFollowing: Int,
    @SerializedName("public_repos") val totalRepository: Int,
    @SerializedName("type") val type: String
)