package com.setianjay.githubuser.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.setianjay.githubuser.database.presistence.entity.User
import com.setianjay.githubuser.databinding.ItemUserListBinding
import com.setianjay.githubuser.utill.load

class UserFavoriteAdapter(private val listener: OnUserFavoriteAdapterListener) :
    RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>() {
    private var usersFavorite = emptyList<User>()

    interface OnUserFavoriteAdapterListener {
        fun onClick(user: User)
        fun onLongClick(user: User)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserFavoriteAdapter.ViewHolder = ViewHolder(
        ItemUserListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: UserFavoriteAdapter.ViewHolder, position: Int) {
        val user = usersFavorite[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = usersFavorite.size

    inner class ViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                ivUser.load(user.avatar)
                tvUser.text = user.username
                tvLocation.text = user.type

                containerUser.setOnClickListener {
                    listener.onClick(user)
                }

                containerUser.setOnLongClickListener {
                    listener.onLongClick(user)
                    true
                }
            }

        }
    }

    fun setDataUser(data: List<User>) {
        val diffCallback = UserFavoriteDiffCallback(this.usersFavorite, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.usersFavorite = data
        diffResult.dispatchUpdatesTo(this)
    }
}