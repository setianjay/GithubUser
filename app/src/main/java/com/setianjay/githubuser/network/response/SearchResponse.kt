package com.setianjay.githubuser.network.response

import com.google.gson.annotations.SerializedName
import com.setianjay.githubuser.model.user.UsersModel

data class SearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val users: List<UsersModel>
)