package com.setianjay.githubuser.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.setianjay.githubuser.databinding.ItemUserListBinding
import com.setianjay.githubuser.model.user.UserModel
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.utill.load
import timber.log.Timber

class UserListAdapter(private val listener: OnUserListAdapterListener) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private val users: ArrayList<UsersModel> = arrayListOf()

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
            user.avatar?.let { binding.ivUser.load(it) }

            binding.tvUser.text = user.username
            binding.tvLocation.text = user.type

            binding.containerUser.setOnClickListener { listener.onClick(user) }
        }
    }

    fun setDataUser(data: List<UsersModel>) {
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }
}