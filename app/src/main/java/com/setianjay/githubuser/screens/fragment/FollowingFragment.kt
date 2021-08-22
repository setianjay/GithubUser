package com.setianjay.githubuser.screens.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.setianjay.githubuser.databinding.FragmentFollowingBinding
import com.setianjay.githubuser.model.user.UserManager
import com.setianjay.githubuser.model.user.UserModel
import com.setianjay.githubuser.screens.adapter.UserListAdapter

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserListAdapter
    private lateinit var userData: List<UserModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        showFollowers()
    }

    private fun setupRecycleView(){
        userData = UserManager(resources).dataUser()
        userAdapter = UserListAdapter(object: UserListAdapter.OnUserListAdapterListener{
            override fun onClick(data: UserModel) {

            }
        })

        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    private fun showFollowers(){
        userAdapter.setDataUser(userData)
    }
}