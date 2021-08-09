package com.setianjay.githubuser.model.user

import android.content.res.Resources
import android.content.res.TypedArray
import com.setianjay.githubuser.R

class UserManager(resources: Resources) {
    private val username: Array<String> = resources.getStringArray(R.array.username)
    private val name: Array<String> = resources.getStringArray(R.array.name)
    private val location: Array<String> = resources.getStringArray(R.array.location)
    private val repository: IntArray = resources.getIntArray(R.array.repository)
    private val company: Array<String> = resources.getStringArray(R.array.company)
    private val followers: IntArray = resources.getIntArray(R.array.followers)
    private val following: IntArray = resources.getIntArray(R.array.following)
    private val avatar: TypedArray = resources.obtainTypedArray(R.array.avatar)

    fun dataUser(): List<UserModel>{
        val listUser: ArrayList<UserModel> = arrayListOf()
            for (i in username.indices){
                val user = UserModel(
                    username = username[i],
                    name = name[i],
                    location = location[i],
                    repository = repository[i],
                    company = company[i],
                    followers = followers[i],
                    following = following[i],
                    avatar = avatar.getResourceId(i, -1)
                )
                listUser.add(user)
            }
        return listUser.sortedBy { it.name }
    }
}