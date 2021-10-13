package com.setianjay.githubuser.screens.adapter

import androidx.recyclerview.widget.DiffUtil
import com.setianjay.githubuser.model.user.UsersModel

class UserListDiffCallback(
    private val oldUsers: List<UsersModel>,
    private val newUsers: List<UsersModel>
    ): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldUsers.size

    override fun getNewListSize(): Int = newUsers.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUsers[oldItemPosition].username == newUsers[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUsers[oldItemPosition].username == newUsers[newItemPosition].username
    }
}