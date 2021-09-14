package com.setianjay.githubuser.screens.common.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.setianjay.githubuser.screens.fragment.FollowersFragment
import com.setianjay.githubuser.screens.fragment.FollowingFragment

class ProfileTabLayout(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    username: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments: List<Fragment> = listOf(
        FollowersFragment.newInstance(username),
        FollowingFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}