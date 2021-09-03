package com.setianjay.githubuser.model.user

import com.google.gson.annotations.SerializedName

data class UserDetailsModel(
    @SerializedName("avatar_url") val avatar: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("email") val email: String?,
    @SerializedName("gravatar_id") val gravatarId: String?,
    @SerializedName("hireable") val hireable: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("name") val name: String,
    @SerializedName("node_id") val nodeId: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("blog") val urlBlog: String?,
    @SerializedName("events_url") val urlEvents: String?,
    @SerializedName("html_url") val urlHtml: String?,
    @SerializedName("followers_url") val urlFollowers: String?,
    @SerializedName("following_url") val urlFollowing: String?,
    @SerializedName("gists_url") val urlGists: String?,
    @SerializedName("organizations_url") val urlOrganizations: String?,
    @SerializedName("received_events_url") val urlReceivedEvents: String?,
    @SerializedName("repos_url") val urlRepos: String?,
    @SerializedName("starred_url") val urlStarred: String?,
    @SerializedName("subscriptions_url") val urlSubscriptions: String?,
    @SerializedName("login") val username: String,
    @SerializedName("id") val userId: Int,
    @SerializedName("site_admin") val siteAdmin: String?,
    @SerializedName("followers") val totalFollowers: Int,
    @SerializedName("following") val totalFollowing: Int,
    @SerializedName("public_gists") val totalGists: Int,
    @SerializedName("public_repos") val totalRepository: Int,
    @SerializedName("twitter_username") val twitter: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("update_at") val updateAt: String
)