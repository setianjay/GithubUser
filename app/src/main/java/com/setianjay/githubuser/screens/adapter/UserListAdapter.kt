package com.setianjay.githubuser.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.setianjay.githubuser.databinding.ItemUserListBinding
import com.setianjay.githubuser.model.user.UserModel
import timber.log.Timber

class UserListAdapter() : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private val users: ArrayList<UserModel> = arrayListOf()

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
        holder.usersBind(user = user)
    }

    override fun getItemCount(): Int  = users.size

    class ViewHolder(private val binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root){
        fun usersBind(user: UserModel){
            Timber.e("avatar user : ${user.avatar}")
           Glide
               .with(binding.ivUser.context)
               .load(user.avatar)
               .into(binding.ivUser)

            binding.tvUser.text = user.name
            binding.tvLocation.text = user.location
        }
    }

    fun setDataUser(data: List<UserModel>){
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }
}