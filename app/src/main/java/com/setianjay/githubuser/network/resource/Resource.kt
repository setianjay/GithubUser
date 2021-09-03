package com.setianjay.githubuser.network.resource

import androidx.annotation.StringRes

class Resource<T>(val statusType: StatusType, val data: T?, @StringRes val message: Int?){
    enum class StatusType{
        ERROR,
        LOADING,
        SUCCESS
    }

    companion object{
        fun <T> error(message: Int): Resource<T>{
            return Resource(StatusType.ERROR, null, message)
        }

        fun <T> loading(): Resource<T>{
            return Resource(StatusType.LOADING, null, null)
        }

        fun <T> success(data: T?): Resource<T>{
            return Resource(StatusType.SUCCESS, data, null)
        }
    }

}
