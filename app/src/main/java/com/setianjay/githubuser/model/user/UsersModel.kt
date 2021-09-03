package com.setianjay.githubuser.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersModel(
    @SerializedName("avatar_url") val avatar: String?,
    @SerializedName("gravatar_id") val gravatarId: String?,
    @SerializedName("node_id") val nodeId: String?,
    @SerializedName("url") val url: String?,
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
    @SerializedName("score") val score: Double?,
    @SerializedName("site_admin") val siteAdmin: String?,
    @SerializedName("type") val type: String?
): Parcelable
