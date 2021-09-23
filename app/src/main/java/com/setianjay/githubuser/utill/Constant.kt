package com.setianjay.githubuser.utill

import com.setianjay.githubuser.BuildConfig

object Constant {
    object API {
        const val BASE_URL = "https://api.github.com/"
    }

    object DATABASE{
        const val DB_NAME = "app_db"
    }

    object ERROR{
        const val ERR_API = -1
        const val ERR_USERS_NOT_FOUND = -2
    }

    object INFO {
        const val IMG_SEARCH_INFORMATION = 1
        const val IMG_NOT_FOUND_INFORMATION = 2
    }
}