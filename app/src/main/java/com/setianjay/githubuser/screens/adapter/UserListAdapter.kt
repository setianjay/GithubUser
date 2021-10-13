package com.setianjay.githubuser.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.setianjay.githubuser.databinding.ItemUserListBinding
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.utill.load

class UserListAdapter(private val listener: OnUserListAdapterListener) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private var users = emptyList<UsersModel>()

    interface OnUserListAdapterListener {
        fun onClick(data: UsersModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.usersBind(user = user, listener)
    }

    override fun getItemCount(): Int = users.size

    class ViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun usersBind(user: UsersModel, listener: OnUserListAdapterListener) {
            binding.apply {
                ivUser.load(user.avatar)
                tvUser.text = user.username
                tvLocation.text = user.type

                containerUser.setOnClickListener { listener.onClick(user) }
            }


        }
    }

    fun setDataUser(data: List<UsersModel>) {
        val diffCallback = UserListDiffCallback(this.users, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.users = data
        diffResult.dispatchUpdatesTo(this)
    }
}