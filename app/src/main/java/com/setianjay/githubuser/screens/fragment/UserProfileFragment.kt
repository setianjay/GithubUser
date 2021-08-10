package com.setianjay.githubuser.screens.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.setianjay.githubuser.R
import com.setianjay.githubuser.databinding.FragmentUserProfileBinding
import com.setianjay.githubuser.model.user.UserModel
import com.setianjay.githubuser.screens.activity.HomeActivity
import com.setianjay.githubuser.screens.common.tablayout.ProfileTabLayout

class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private var data: UserModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataUser()
        setupTabLayout()
    }

    private fun dataUser(){
        val bundle = arguments
        if (bundle != null) {
            data = bundle.getParcelable(EXTRA_PROFILE)
            showProfile(data)
        }
    }

    private fun showProfile(data: UserModel?) {
        Glide
            .with(binding.ivProfile.context)
            .load(data?.avatar)
            .into(binding.ivProfile)

        binding.tvRepository.text = data?.repository.toString()
        binding.tvFollowers.text = data?.followers.toString()
        binding.tvFollowing.text = data?.following.toString()
        binding.tvName.text = data?.name
        binding.tvUsername.text = data?.username
        binding.tvCompany.text = data?.company
        binding.tvLocation.text = data?.location
    }

    private fun setupTabLayout(){
        val tabAdapter = ProfileTabLayout(childFragmentManager,lifecycle)
        binding.vwPager.adapter = tabAdapter

        val titlesTabLayout = listOf("Followers","Following")
        TabLayoutMediator(binding.tbLayout,binding.vwPager){
                tab, position -> tab.text = titlesTabLayout[position]
        }.attach()
    }

    companion object{
        const val EXTRA_PROFILE = "extra_profile"
    }
}