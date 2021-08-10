package com.setianjay.githubuser.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val username: String,
    val name: String,
    val location: String,
    val repository: Int,
    val company: String,
    val followers: Int,
    val following: Int,
    val avatar: Int
): Parcelable