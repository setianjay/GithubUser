package com.setianjay.githubuser.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersModel(
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("login") val username: String,
    @SerializedName("type") val type: String?
): Parcelable
